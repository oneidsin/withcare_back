package com.withcare.profile.dto;

// 사용자의 활동 내역을 가져와서 담는 DTO
public class MemberActivityDTO {
    private int post_count;
    private int comment_count;
    private int like_count;
    private int timeline_count;
    private int access_count;

    public int getPost_count() {
        return post_count;
    }

    public void setPost_count(int post_count) {
        this.post_count = post_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getTimeline_count() {
        return timeline_count;
    }

    public void setTimeline_count(int timeline_count) {
        this.timeline_count = timeline_count;
    }

    public int getAccess_count() {
        return access_count;
    }

    public void setAccess_count(int access_count) {
        this.access_count = access_count;
    }
}
