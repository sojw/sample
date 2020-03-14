package com.sample.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.input.BOMInputStream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResourceLoaderSupporter {
	private final ResourceLoader resourceLoader;

	public Reader getReader(String filename) throws IOException {
		Resource resource = resourceLoader.getResource(filename);
		if (resource == null) {
			return null;
		}
		return new BufferedReader(new InputStreamReader(new BOMInputStream(resource.getInputStream()), "UTF-8"));
	}

	public List<String> loadFileContents(String filename) throws IOException {
		return loadFileContents(resourceLoader.getResource(filename));
	}

	private List<String> loadFileContents(Resource resource) throws IOException {
		InputStream inputStream = resource.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		List<String> builder = Lists.newArrayList();
		String line;
		while ((line = reader.readLine()) != null) {
			builder.add(line);
		}
		reader.close();

		return builder;
	}
}