package com.sample.api;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sample.api.config.SampleApiConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SampleApiConfiguration.class})
@Transactional
public abstract class IntegrationTest {

}