package com.goCamping.domain;

// 게시글 조회 쿼리에 전달될 파리미터를 담게 될 클래스이다.
public class Criteria {
	
	// 현재 페이지 번호
	private int page;
	// 한 페이지당 보여줄 게시글의 갯수
	private int perPageNum;
	
	public int getPageStart() {
		// MySql의 LIMIT 구문 사용
		// 1 페이지 -> 00행 ~ 09행 ( 10개 ) : LIMIT 0, 10
		// 2 페이지 -> 10행 ~ 19행 ( 10개 ) : LIMIT 10, 10
		// 3 페이지 -> 20행 ~ 29행 ( 10개 ) : LIMIT 20, 10
		return (this.page-1) * perPageNum;
	}
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}
	
	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1;
		} else {
			this.page = page;
		}
	}
	public void setPerPageNum(int pageCount) {
		int cnt = this.perPageNum;
		if(pageCount != cnt) {
			this.perPageNum = cnt;
		} else {
			this.perPageNum = pageCount;
		}
	}

	public int getPage() {
		return page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}
	
	
}
