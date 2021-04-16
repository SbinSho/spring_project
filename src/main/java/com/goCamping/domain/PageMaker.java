package com.goCamping.domain;

// 페이징에 관련된 버튼들을 만드는 기능을 하는 클래스
public class PageMaker {

	private Criteria cri;
	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private int displayPageNnum = 10;
	
	public Criteria getCri() {
		return cri;
	}
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	
	private void calcData() {
		// 끝 페이지 번호 = ( 현재 페이지 번호 / 화면에 보여질 페이지 번호의 갯수 ) * 화면에 보여질 페이지 번호의 갯수 
		endPage = (int) ( Math.ceil(cri.getPage() / (double) displayPageNnum ) * displayPageNnum);
		// 시작 페이지 번호 = ( 끝 페이지 번호 - 화면에 보여질 페이지 번호의 갯수 ) + 1
		startPage = (endPage - displayPageNnum) + 1;
		
		if(startPage <= 0) startPage = 1;
		
		// 마지막 페이지 번호 = 총 게시글 수 / 한 페이지당 보여줄 게시글의 갯수 
		int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
		if( endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		// 이전 버튼 생성 여부 = 시작 페이지 번호 == 1 ? false : true
		prev = startPage == 1 ? false : true;
		// 다음 버튼 생성 여부 = 끝 페이지 번호 * 한 페이지당 보여줄 게시글의 갯수 < 총 게시글의 수 ? true : false
		next = endPage * cri.getPerPageNum() < totalCount ? true : false;
	}
	
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNnum() {
		return displayPageNnum;
	}
	public void setDisplayPageNnum(int displayPageNnum) {
		this.displayPageNnum = displayPageNnum;
	}
	
	@Override
	public String toString() {
		return "PageMaker [cri=" + cri + ", totalCount=" + totalCount + ", startPage=" + startPage + ", endPage="
				+ endPage + ", prev=" + prev + ", next=" + next + ", displayPageNnum=" + displayPageNnum + "]";
	}
	
	
}
