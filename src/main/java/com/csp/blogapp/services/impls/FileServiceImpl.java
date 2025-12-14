package com.csp.blogapp.services.impls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csp.blogapp.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile multipartFile) throws IOException {
		// file name like abc.png
		String filename = multipartFile.getOriginalFilename();

		// Random name generate for file
		String rarndomName = UUID.randomUUID().toString();
		String fullFileName = rarndomName.concat(filename.substring(filename.lastIndexOf(".")));

		// Full path
		String filePath = path + File.separator + fullFileName;

		// Create File if not created before
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}

		// File Copy
		Files.copy(multipartFile.getInputStream(), Paths.get(filePath));

		return fullFileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
	}

}
