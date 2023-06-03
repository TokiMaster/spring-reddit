package swt.reddit.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;

@Document(indexName = "reddit_posts")
@Setting(settingPath = "analyzers/serbianAnalyzer.json")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class IndexPost {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String fileName;

    @Field(type = FieldType.Text)
    private String pdfContent;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String text;

    public IndexPost(Post post, String pdfContent, String fileName) {
        this.id = post.getId();
        this.text = post.getText();
        this.title = post.getTitle();
        this.pdfContent = pdfContent;
        this.fileName = fileName;
    }

    public IndexPost(Post post) {
        this.id = post.getId();
        this.text = post.getText();
        this.title = post.getTitle();
    }

}
