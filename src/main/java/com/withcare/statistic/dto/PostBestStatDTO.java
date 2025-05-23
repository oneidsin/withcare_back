package com.withcare.statistic.dto;

import java.sql.Timestamp;

public class PostBestStatDTO {
	
	// 주간 인기 게시글 통계
		private int post_idx;
		private String post_title;
		private String id;
		private int post_view_cnt;
		private int like_count;
		private Timestamp post_create_date;

		public int getPost_idx() {
			return post_idx;
		}

		public void setPost_idx(int post_idx) {
			this.post_idx = post_idx;
		}

		public String getPost_title() {
			return post_title;
		}

		public void setPost_title(String post_title) {
			this.post_title = post_title;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getPost_view_cnt() {
			return post_view_cnt;
		}

		public void setPost_view_cnt(int post_view_cnt) {
			this.post_view_cnt = post_view_cnt;
		}

		public int getLike_count() {
			return like_count;
		}

		public void setLike_count(int like_count) {
			this.like_count = like_count;
		}

		public Timestamp getPost_create_date() {
			return post_create_date;
		}

		public void setPost_create_date(Timestamp post_create_date) {
			this.post_create_date = post_create_date;
		}

}
