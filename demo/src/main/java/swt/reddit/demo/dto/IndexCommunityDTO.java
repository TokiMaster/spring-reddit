package swt.reddit.demo.dto;

public class IndexCommunityDTO {

    private final Long id;

    private final String name;

    private final String description;

    private final String fileName;

    public IndexCommunityDTO(Long id, String name, String description, String fileName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFileName() {
        return fileName;
    }
}
