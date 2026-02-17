package com.ezen.biz.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadStorageService {

	private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");
	private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/jpeg", "image/png", "image/gif", "image/webp");

	private static final Path BASE_UPLOAD_DIR = Paths.get(System.getProperty("user.home"), ".kozynest", "uploads");
	private static final Path ACCOMMODATION_UPLOAD_DIR = BASE_UPLOAD_DIR.resolve("accommodation_images");
	private static final Path ROOM_UPLOAD_DIR = BASE_UPLOAD_DIR.resolve("room_images");

	public String storeAccommodationImage(MultipartFile file) {
		return store(file, ACCOMMODATION_UPLOAD_DIR);
	}

	public String storeRoomImage(MultipartFile file) {
		return store(file, ROOM_UPLOAD_DIR);
	}

	private String store(MultipartFile file, Path targetDir) {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("Uploaded file is empty.");
		}

		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null || originalFilename.trim().isEmpty()) {
			throw new IllegalArgumentException("Uploaded file name is invalid.");
		}

		String extension = extractExtension(originalFilename);
		if (!ALLOWED_EXTENSIONS.contains(extension)) {
			throw new IllegalArgumentException("Unsupported file extension. Allowed: jpg, jpeg, png, gif, webp.");
		}

		String contentType = file.getContentType();
		if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase(Locale.ROOT))) {
			throw new IllegalArgumentException("Unsupported file type.");
		}

		if (!hasValidMagicSignature(file, extension)) {
			throw new IllegalArgumentException("File content does not match allowed image signatures.");
		}

		String randomFileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
		Path outputPath = targetDir.resolve(randomFileName);

		try {
			Files.createDirectories(targetDir);
			try (InputStream in = file.getInputStream()) {
				Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new IllegalStateException("Failed to store uploaded file.", e);
		}

		return randomFileName;
	}

	private String extractExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex < 0 || dotIndex == fileName.length() - 1) {
			throw new IllegalArgumentException("File extension is missing.");
		}
		return fileName.substring(dotIndex + 1).toLowerCase(Locale.ROOT);
	}

	private boolean hasValidMagicSignature(MultipartFile file, String extension) {
		byte[] header = new byte[12];
		int bytesRead;
		try (InputStream in = file.getInputStream()) {
			bytesRead = in.read(header);
		} catch (IOException e) {
			return false;
		}

		if (bytesRead < 4) {
			return false;
		}

		switch (extension) {
		case "jpg":
		case "jpeg":
			return (header[0] & 0xFF) == 0xFF && (header[1] & 0xFF) == 0xD8 && (header[2] & 0xFF) == 0xFF;
		case "png":
			return (header[0] & 0xFF) == 0x89 && header[1] == 0x50 && header[2] == 0x4E && header[3] == 0x47;
		case "gif":
			return header[0] == 0x47 && header[1] == 0x49 && header[2] == 0x46 && header[3] == 0x38;
		case "webp":
			if (bytesRead < 12) {
				return false;
			}
			return header[0] == 0x52 && header[1] == 0x49 && header[2] == 0x46 && header[3] == 0x46
					&& header[8] == 0x57 && header[9] == 0x45 && header[10] == 0x42 && header[11] == 0x50;
		default:
			return false;
		}
	}
}
