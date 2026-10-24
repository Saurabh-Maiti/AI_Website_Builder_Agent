
package org.example.ai_websitebuilder_agent.ai_tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class WebsiteBuilderTools {

    private final Path workPath =
            Path.of("generated-sites").toAbsolutePath().normalize();

    public WebsiteBuilderTools() {
    }

    // Resolve a relative path safely inside the workspace.
    private Path resolvePath(String relativePath) throws IOException {
        if (relativePath == null || relativePath.isBlank()) {
            throw new IOException("Path cannot be empty.");
        }

        Path requested = Path.of(relativePath);

        if (requested.isAbsolute()) {
            throw new IOException("Absolute paths are not allowed.");
        }

        Path resolved = workPath.resolve(requested).normalize();

        if (!resolved.startsWith(workPath)
                || resolved.equals(workPath)) {
            throw new IOException("Path is outside the workspace.");
        }

        // Reject symbolic links in the existing path.
        Path current = workPath;

        for (Path part : workPath.relativize(resolved)) {
            current = current.resolve(part);

            if (Files.isSymbolicLink(current)) {
                throw new IOException(
                        "Symbolic links are not allowed."
                );
            }
        }

        return resolved;
    }

    @Tool(description = """
            Create a directory inside the website workspace.
            Provide a relative path such as my-site or my-site/css.
            Parent directories are created automatically.
            """)
    public String createDirectory(String directoryPath) {
        try {
            Path directory = resolvePath(directoryPath);

            Files.createDirectories(directory);

            return "Directory created: "
                    + workPath.relativize(directory);

        } catch (IOException | RuntimeException e) {
            return "Failed to create directory: " + e.getMessage();
        }
    }

    @Tool(description = """
            Create or overwrite a UTF-8 text file inside the website
            workspace. Provide a relative file path and the complete
            text content. Parent directories are created automatically.
            """)
    public String writeFile(String filePath, String content) {
        try {
            if (content == null) {
                return "Failed to write file: content cannot be null.";
            }

            Path file = resolvePath(filePath);

            Files.createDirectories(file.getParent());

            Files.writeString(
                    file,
                    content,
                    StandardCharsets.UTF_8
            );

            return "File written successfully: "
                    + workPath.relativize(file);

        } catch (IOException | RuntimeException e) {
            return "Failed to write file: " + e.getMessage();
        }
    }

    @Tool(description = """
            Read a UTF-8 text file from the website workspace.
            Provide a relative path such as my-site/index.html.
            """)
    public String readFile(String filePath) {
        try {
            Path file = resolvePath(filePath);

            if (!Files.isRegularFile(file, LinkOption.NOFOLLOW_LINKS)) {
                return "Failed to read file: path is not a regular file.";
            }

            return Files.readString(file, StandardCharsets.UTF_8);

        } catch (IOException | RuntimeException e) {
            return "Failed to read file: " + e.getMessage();
        }
    }

    @Tool(description = """
            List files and directories directly inside a directory
            in the website workspace. Provide a relative directory path.
            """)
    public String listFiles(String directoryPath) {
        try {
            Path directory = resolvePath(directoryPath);

            if (!Files.isDirectory(
                    directory, LinkOption.NOFOLLOW_LINKS)) {
                return "Failed to list files: path is not a directory.";
            }

            try (Stream<Path> entries = Files.list(directory)) {
                String result = entries
                        .sorted(Comparator.comparing(
                                path -> path.getFileName().toString()))
                        .map(path -> {
                            String name = path.getFileName().toString();

                            if (Files.isDirectory(
                                    path, LinkOption.NOFOLLOW_LINKS)) {
                                return name + "/";
                            }

                            return name;
                        })
                        .collect(Collectors.joining("\n"));

                return result.isEmpty()
                        ? "Directory is empty."
                        : result;
            }

        } catch (IOException | RuntimeException e) {
            return "Failed to list files: " + e.getMessage();
        }
    }
}
