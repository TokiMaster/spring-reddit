package swt.reddit.demo.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;

@Document(indexName = "reddit_posts")
@Setting(settingPath = "analyzers/serbianAnalyzer.json")
@Data
public class IndexPost {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String descriptionPDF;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String text;

    public IndexPost(Post post, String descriptionPDF) {
        this.id = post.getId();
        this.text = post.getText();
        this.title = post.getTitle();
        this.descriptionPDF = descriptionPDF;
    }

    public IndexPost(Post post) {
        this.id = post.getId();
        this.text = post.getText();
        this.title = post.getTitle();
    }

}
