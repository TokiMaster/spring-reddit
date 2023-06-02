package swt.reddit.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;

@Document(indexName = "reddit_community")
@Setting(settingPath = "analyzers/serbianAnalyzer.json")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IndexCommunity {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private String pdfContent;

    public IndexCommunity(Community community) {
        this.id = community.getId();
        this.description = community.getDescription();
        this.name = community.getName();
    }

    public IndexCommunity(Community community, String pdfContent) {
        this.id = community.getId();
        this.description = community.getDescription();
        this.name = community.getName();
        this.pdfContent = pdfContent;
    }

}
