package org.lynxz.demo.storage


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.stream.Stream

@Service
class FileSystemStorageService @Autowired
constructor(properties: StorageProperties) : StorageService {

    private val rootLocation: Path = Paths.get(properties.location)

    override fun store(file: MultipartFile) {
        val filename = StringUtils.cleanPath(file.originalFilename)
        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file " + filename)
            }
            if (filename.contains("..")) {
                // This is a security check
                throw StorageException(
                        "Cannot store file with relative path outside current directory " + filename)
            }
            Files.copy(file.inputStream, this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING)
        } catch (e: IOException) {
            throw StorageException("Failed to store file " + filename, e)
        }
    }

    override fun loadAll(): Stream<Path> {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter { path -> path != this.rootLocation }
                    .map { path -> this.rootLocation.relativize(path) }
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }
    }

    override fun load(filename: String): Path {
        return rootLocation.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {
        try {
            val file = load(filename)
            val resource = UrlResource(file.toUri())
            return if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw StorageFileNotFoundException(
                        "Could not read file: " + filename)
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: " + filename, e)
        }
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    override fun init() {
        try {
            Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }

    }
}
