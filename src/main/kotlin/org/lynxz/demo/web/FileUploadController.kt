package org.lynxz.demo.web

import org.lynxz.demo.storage.StorageFileNotFoundException
import org.lynxz.demo.storage.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.IOException
import java.util.stream.Collectors


/**
 * Created by lynxz on 22/12/2017.
 * [文档地址](https://spring.io/guides/gs/uploading-files/)
 */
@RestController
class FileUploadController @Autowired constructor(var storageService: StorageService) {

    @GetMapping("/")
    @Throws(IOException::class)
    fun listUploadedFiles(model: Model): String {
        model.addAttribute("files", storageService.loadAll().map { path ->
            MvcUriComponentsBuilder.fromMethodName(FileUploadController::class.java,
                    "serveFile", path.fileName.toString()).build().toString()
        }
                .collect(Collectors.toList<Any>()))
        return "uploadForm"
    }

    /**
     * 下载指定名称的文件
     * 如: http://localhost:8080/files/photo.jpg
     * */
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    fun serveFile(@PathVariable filename: String): ResponseEntity<Resource> {

        val file = storageService.loadAsResource(filename)
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.filename + "\"").body(file)
    }

    /**
     * 上传文件
     * post方式 localhost:8080/ body中传入file信息
     * */
    @PostMapping("/")
    fun handleFileUpload(@RequestParam("file") file: MultipartFile, redirectAttributes: RedirectAttributes): String {
        storageService.store(file)
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded ${file.originalFilename}!")
        return "redirect:/"
    }

    @ExceptionHandler(StorageFileNotFoundException::class)
    fun handleStorageFileNoFound(exc: StorageFileNotFoundException): ResponseEntity<*>
            = ResponseEntity.notFound().build<Any>()
}