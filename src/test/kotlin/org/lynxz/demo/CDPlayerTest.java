package org.lynxz.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lynxz.demo.bean.CompactDisc;
import org.lynxz.demo.bean.CDPlayerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lynxz on 25/12/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig.class)
public class CDPlayerTest {

    @Autowired
    private CompactDisc cd;

    @Test
    public void cdShouldNotBeNull() {
        Assert.assertNotNull(cd);
    }
}
