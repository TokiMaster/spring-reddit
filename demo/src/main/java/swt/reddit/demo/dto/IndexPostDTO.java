package swt.reddit.demo.dto;

public class IndexPostDTO {

    private final Long id;

    private final String fileName;

    private final String title;

    private final String text;

    public IndexPostDTO(Long id, String fileName, String title, String text) {
        this.id = id;
        this.fileName = fileName;
        this.title = title;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

}
