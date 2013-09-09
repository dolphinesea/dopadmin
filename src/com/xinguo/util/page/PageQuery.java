package com.xinguo.util.page;

public interface PageQuery<T_Criterial, T_OrderBy> {
	public abstract PageInfo<T_Criterial, T_OrderBy> generatePageInfo();
}
