package com.sayed.assaignmentproject.model;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.DiffUtil;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sayed.assaignmentproject.services.local.DBhelper;
import com.sayed.assaignmentproject.services.local.DbUtilities;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static com.sayed.assaignmentproject.services.local.DbUtilities.COLUMN_TIMESTAMP;

/*here base observable is for data binding
we will use different annotation of jackson
* */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"userId","id","title","body"})
public class Post extends BaseObservable {
    @JsonProperty("id")
    private Integer id ;
    @JsonProperty("userId")
    private Integer userId ;
    @JsonProperty("title")
    private String title;
    @JsonProperty("body")
    private String body;
    @JsonProperty(COLUMN_TIMESTAMP)
    private Timestamp timestamp ;


    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    @Bindable
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }
    @JsonProperty("id")
    public Post setId(Integer id) {
        this.id = id;
        return this ;
    }
    @Bindable
    @JsonProperty("userId")
    public Integer getUserId() {
        return userId;
    }
    @JsonProperty("userId")
    public Post setUserId(Integer userId) {
        this.userId = userId;
        return this ;
    }
    @Bindable
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }
    @JsonProperty("title")
    public Post setTitle(String title) {
        this.title = title;
        return this ;
    }
    @Bindable
    @JsonProperty("body")
    public String getBody() {
        return body;
    }
    @JsonProperty("body")
    public Post setBody(String body) {
        this.body = body;
        return this ;
    }
    @Bindable
    @JsonProperty(COLUMN_TIMESTAMP)
    public Timestamp getTimestamp() {
        return timestamp;
    }
    @JsonProperty(COLUMN_TIMESTAMP)
    public Post setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this ;
    }

    public static DiffUtil.ItemCallback<Post> postItemCallback = new DiffUtil.ItemCallback<Post>() {
        @Override
        public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getId() == newItem.getId() ;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getBody().equals(newItem.getBody());
        }
    };


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
