/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.test;

import ar.com.fdvs.dj.test.domain.Product;
import ar.com.fdvs.dj.util.SortUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TestRepositoryProducts {

	public static List<Product> getDummyCollection(){

		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("dd/MM/yyyy");

		List<Product> col =  new ArrayList<Product>();

		//The collection is ordered by State, Branch and Product Line
		col.add(new Product( 1L,"book","Harry Potter 7","Florida","Main Street", 2500L, 10000f));
		col.add(new Product( 1L,"book","Harry Potter 7","Florida","Railway Station", 1400L, 2831.32f));
		col.add(new Product( 1L,"book","Harry Potter 7","Florida","Baseball Stadium", 4000L, 38347f));
		col.add(new Product( 1L,"book","Harry Potter 7","Florida","Shopping Center", 3000L, 9482.4f));
		col.add(new Product( 1L,"book","Harry Potter 7","New York","Main Street", 2500L, 27475.5f));
		col.add(new Product( 1L,"book","Harry Potter 7","New York","Railway Station", 1400L, 3322f));
		col.add(new Product( 1L,"book","Harry Potter 7","New York","Baseball Stadium", 4000L, 78482f));
		col.add(new Product( 1L,"book","Harry Potter 7","New York","Shopping Center", 3000L, 5831.32f));
//		col.add(new Product( 1L,"book","Harry Potter 7","Washington","Main Street", 1500L, 78482f));
//		col.add(new Product( 1L,"book","Harry Potter 7","Washington","Railway Station", 8400L, 2831.32f));
//		col.add(new Product( 1L,"book","Harry Potter 7","Washington","Baseball Stadium", 1400L, 38347f));
//		col.add(new Product( 1L,"book","Harry Potter 7","Washington","Shopping Center", 3000L, 8329.2f));
//		col.add(new Product( 1L,"book","Harry Potter 7","Arizona","Main Street", 1500L, 27475.5f));
//		col.add(new Product( 1L,"book","Harry Potter 7","Arizona","Railway Station", 4000L, 3322f));
//		col.add(new Product( 1L,"book","Harry Potter 7","Arizona CustomExpressions can't be grouped (it doesn't crash, but there's always just 1 group in the output even when there should be more). ","Baseball Stadium", 3000L, 78482f));
//		col.add(new Product( 1L,"book","Harry Potter 7","Arizona CustomExpressions can't be grouped (it doesn't crash, but there's always just 1 group in the output even when there should be more). ","Shopping Center", 1500L, 5831.32f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Florida","Main Street", 8400L, 78482f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Florida","Railway Station", 1400L, 2831.32f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Florida","Baseball Stadium", 4000L, 38347f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Florida","Shopping Center", 3000L, 9482.4f));
		col.add(new Product( 2L,"book","The Sum of All Fears","New York","Main Street", 1500L, 8329.2f));
		col.add(new Product( 2L,"book","The Sum of All Fears","New York","Railway Station", 2500L, 27475.5f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","New York","Baseball Stadium", 1400L, 3322f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","New York","Shopping Center", 1500L, 78482f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Washington","Main Street", 2500L, 5831.32f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Washington","Railway Station", 1400L, 78482f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Washington","Baseball Stadium", 4000L, 2831.32f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Washington","Shopping Center", 3000L, 38347f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Arizona CustomExpressions can't be grouped (it doesn't crash, but there's always just 1 group in the output even when there should be more). ","Main Street", 4000L, 9482.4f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Arizona CustomExpressions can't be grouped (it doesn't crash, but there's always just 1 group in the output even when there should be more). ","Railway Station", 3000L, 8329.2f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Arizona CustomExpressions can't be grouped (it doesn't crash, but there's always just 1 group in the output even when there should be more). ","Baseball Stadium", 1500L, 27475.5f));
//		col.add(new Product( 2L,"book","The Sum of All Fears","Arizona CustomExpressions can't be grouped (it doesn't crash, but there's always just 1 group in the output even when there should be more). ","Shopping Center", 8400L, 3322f));
//		col.add(new Product( 3L,"book","The Pelican Brief,","Florida","Main Street", 1400L, 78482f));
//		col.add(new Product( 3L,"book","The Pelican Brief,","Florida","Railway Station", 4000L, 5831.32f));
//		col.add(new Product( 3L,"book","The Pelican Brief,","Florida","Baseball Stadium", 3000L, 78482f));
//		col.add(new Product( 3L,"book","The Pelican Brief,","Florida","Shopping Center", 1500L, 2831.32f));
		col.add(new Product( 3L,"book","The Pelican Brief,","New York","Main Street", 2500L, 38347f));
		col.add(new Product( 3L,"book","The Pelican Brief,","New York","Railway Station", 1400L, 9482.4f));
		col.add(new Product( 3L,"book","The Pelican Brief,","New York","Baseball Stadium", 1500L, 8329.2f));
		col.add(new Product( 3L,"book","The Pelican Brief,","New York","Shopping Center", 2500L, 27475.5f));
		col.add(new Product( 3L,"book","The Pelican Brief,","Washington","Main Street", 1400L, 3322f));
		col.add(new Product( 3L,"book","The Pelican Brief,","Washington","Railway Station", 4000L, 78482f));
		col.add(new Product( 3L,"book","The Pelican Brief,","Washington","Baseball Stadium", 3000L, 5831.32f));
		col.add(new Product( 3L,"book","The Pelican Brief,","Washington","Shopping Center", 4000L, 78482f));
		col.add(new Product( 3L,"book","The Pelican Brief,","Arizona","Main Street", 3000L, 2831.32f));
		col.add(new Product( 3L,"book","The Pelican Brief,","Arizona","Railway Station", 1500L, 38347f));
//		col.add(new Product( 3L,"book","The Pelican Brief,","Arizona","Baseball Stadium", 8400L, 9482.4f));
		col.add(new Product( 3L,"book","The Pelican Brief,","Arizona","Shopping Center", 1400L, 8329.2f));
		col.add(new Product( 4L,"dvd","Titanic","Florida","Main Street", 4000L, 27475.5f));
		col.add(new Product( 4L,"dvd","Titanic","Florida","Railway Station", 3000L, 3322f));
		col.add(new Product( 4L,"dvd","Titanic","Florida","Baseball Stadium", 1500L, 78482f));
		col.add(new Product( 4L,"dvd","Titanic","Florida","Shopping Center", 2500L, 5831.32f));
		col.add(new Product( 4L,"dvd","Titanic","New York","Main Street", 1400L, 78482f));
		col.add(new Product( 4L,"dvd","Titanic","New York","Railway Station", 1500L, 2831.32f));
		col.add(new Product( 4L,"dvd","Titanic","New York","Baseball Stadium", 2500L, 38347f));
		col.add(new Product( 4L,"dvd","Titanic","New York","Shopping Center", 1400L, 9482.4f));
		col.add(new Product( 4L,"dvd","Titanic","Washington","Main Street", 4000L, 8329.2f));
		col.add(new Product( 4L,"dvd","Titanic","Washington","Railway Station", 3000L, 27475.5f));
		col.add(new Product( 4L,"dvd","Titanic","Washington","Baseball Stadium", 4000L, 3322f));
		col.add(new Product( 4L,"dvd","Titanic","Washington","Shopping Center", 3000L, 78482f));
		col.add(new Product( 4L,"dvd","Titanic","Arizona","Main Street", 1500L, 5831.32f));
		col.add(new Product( 4L,"dvd","Titanic","Arizona","Railway Station", 8400L, 3322f));
//		col.add(new Product( 4L,"dvd","Titanic","Arizona","Baseball Stadium", 1400L, 78482f));
		col.add(new Product( 4L,"dvd","Titanic","Arizona","Shopping Center", 4000L, 5831.32f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Florida","Main Street", 3000L, 78482f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Florida","Railway Station", 1500L, 2831.32f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Florida","Baseball Stadium", 2500L, 38347f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Florida","Shopping Center", 1400L, 9482.4f));
		col.add(new Product( 5L,"dvd","Back To the Future","New York","Main Street", 1500L, 8329.2f));
//		col.add(new Product( 5L,"dvd","Back To the Future","New York","Railway Station", 2500L, 27475.5f));
//		col.add(new Product( 5L,"dvd","Back To the Future","New York","Baseball Stadium", 1400L, 3322f));
//		col.add(new Product( 5L,"dvd","Back To the Future","New York","Shopping Center", 4000L, 78482f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Washington","Main Street", 3000L, 5831.32f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Washington","Railway Station", 4000L, 78482f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Washington","Baseball Stadium", 3000L, 2831.32f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Washington","Shopping Center", 1500L, 38347f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Arizona","Main Street", 8400L, 9482.4f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Arizona","Railway Station", 1400L, 8329.2f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Arizona","Baseball Stadium", 4000L, 27475.5f));
//		col.add(new Product( 5L,"dvd","Back To the Future","Arizona","Shopping Center", 3000L, 3322f));
//		col.add(new Product( 6L,"dvd","Monsters Inc","Florida","Main Street", 1500L, 78482f));
//		col.add(new Product( 6L,"dvd","Monsters Inc","Florida","Railway Station", 2500L, 5831.32f));
//		col.add(new Product( 6L,"dvd","Monsters Inc","Florida","Baseball Stadium", 1400L, 3322f));
//		col.add(new Product( 6L,"dvd","Monsters Inc","Florida","Shopping Center", 1500L, 78482f));
//		col.add(new Product( 6L,"dvd","Monsters Inc","New York","Main Street", 2500L, 5831.32f));
		col.add(new Product( 6L,"dvd","Monsters Inc","New York","Railway Station", 1400L, 78482f));
		col.add(new Product( 6L,"dvd","Monsters Inc","New York","Baseball Stadium", 4000L, 2831.32f));
		col.add(new Product( 6L,"dvd","Monsters Inc","New York","Shopping Center", 3000L, 38347f));
		col.add(new Product( 6L,"dvd","Monsters Inc","Washington","Main Street", 4000L, 9482.4f));
		col.add(new Product( 6L,"dvd","Monsters Inc","Washington","Railway Station", 3000L, 8329.2f));
//		col.add(new Product( 6L,"dvd","Monsters Inc","Washington","Baseball Stadium", 1500L, 27475.5f));
//		col.add(new Product( 6L,"dvd","Monsters Inc","Washington","Shopping Center", 8400L, 3322f));
//		col.add(new Product( 6L,"dvd","Monsters Inc","Arizona","Main Street", 1400L, 78482f));
//		col.add(new Product( 6L,"dvd","Monsters Inc","Arizona","Railway Station", 4000L, 5831.32f));
//		col.add(new Product( 6L,"dvd","Monsters Inc","Arizona","Baseball Stadium", 3000L, 78482f));
//		col.add(new Product( 6L,"dvd","Monsters Inc","Arizona","Shopping Center", 1500L, 2831.32f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Florida","Main Street", 2500L, 38347f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Florida","Railway Station", 1400L, 9482.4f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Florida","Baseball Stadium", 1500L, 8329.2f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Florida","Shopping Center", 2500L, 27475.5f));
		col.add(new Product( 7L,"magazine","Sports Illustrated","New York","Main Street", 1400L, 3322f));
		col.add(new Product( 7L,"magazine","Sports Illustrated","New York","Railway Station", 4000L, 78482f));
		col.add(new Product( 7L,"magazine","Sports Illustrated","New York","Baseball Stadium", 3000L, 5831.32f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","New York","Shopping Center", 4000L, 3322f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Washington","Main Street", 3000L, 78482f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Washington","Railway Station", 1500L, 5831.32f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Washington","Baseball Stadium", 8400L, 78482f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Washington","Shopping Center", 1400L, 2831.32f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Arizona","Main Street", 4000L, 38347f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Arizona","Railway Station", 3000L, 9482.4f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Arizona","Baseball Stadium", 1500L, 8329.2f));
//		col.add(new Product( 7L,"magazine","Sports Illustrated","Arizona","Shopping Center", 2500L, 27475.5f));
//		col.add(new Product( 8L,"magazine","The Economist","Florida","Main Street", 1400L, 3322f));
//		col.add(new Product( 8L,"magazine","The Economist","Florida","Railway Station", 1500L, 78482f));
//		col.add(new Product( 8L,"magazine","The Economist","Florida","Baseball Stadium", 2500L, 5831.32f));
//		col.add(new Product( 8L,"magazine","The Economist","Florida","Shopping Center", 1400L, 78482f));
//		col.add(new Product( 8L,"magazine","The Economist","New York","Main Street", 4000L, 2831.32f));
//		col.add(new Product( 8L,"magazine","The Economist","New York","Railway Station", 3000L, 38347f));
//		col.add(new Product( 8L,"magazine","The Economist","New York","Baseball Stadium", 4000L, 9482.4f));
//		col.add(new Product( 8L,"magazine","The Economist","New York","Shopping Center", 3000L, 8329.2f));
//		col.add(new Product( 8L,"magazine","The Economist","Washington","Main Street", 1500L, 27475.5f));
//		col.add(new Product( 8L,"magazine","The Economist","Washington","Railway Station", 8400L, 3322f));
//		col.add(new Product( 8L,"magazine","The Economist","Washington","Baseball Stadium", 1400L, 78482f));
//		col.add(new Product( 8L,"magazine","The Economist","Washington","Shopping Center", 4000L, 5831.32f));
//		col.add(new Product( 8L,"magazine","The Economist","Arizona","Main Street", 3000L, 3322f));
//		col.add(new Product( 8L,"magazine","The Economist","Arizona","Railway Station", 1500L, 78482f));
//		col.add(new Product( 8L,"magazine","The Economist","Arizona","Baseball Stadium", 2500L, 5831.32f));
//		col.add(new Product( 8L,"magazine","The Economist","Arizona","Shopping Center", 1400L, 78482f));
//		col.add(new Product( 9L,"magazine","National Geographic","Florida","Main Street", 1500L, 2831.32f));
//		col.add(new Product( 9L,"magazine","National Geographic","Florida","Railway Station", 2500L, 38347f));
//		col.add(new Product( 9L,"magazine","National Geographic","Florida","Baseball Stadium", 1400L, 9482.4f));
//		col.add(new Product( 9L,"magazine","National Geographic","Florida","Shopping Center", 4000L, 8329.2f));
//		col.add(new Product( 9L,"magazine","National Geographic","New York","Main Street", 3000L, 27475.5f));
//		col.add(new Product( 9L,"magazine","National Geographic","New York","Railway Station", 1400L, 3322f));
//		col.add(new Product( 9L,"magazine","National Geographic","New York","Baseball Stadium", 4000L, 78482f));
//		col.add(new Product( 9L,"magazine","National Geographic","New York","Shopping Center", 3000L, 5831.32f));
//		col.add(new Product( 9L,"magazine","National Geographic","Washington","Main Street", 4000L, 3322f));
//		col.add(new Product( 9L,"magazine","National Geographic","Washington","Railway Station", 3000L, 78482f));
//		col.add(new Product( 9L,"magazine","National Geographic","Washington","Baseball Stadium", 1500L, 5831.32f));
//		col.add(new Product( 9L,"magazine","National Geographic","Washington","Shopping Center", 8400L, 78482f));
//		col.add(new Product( 9L,"magazine","National Geographic","Arizona","Main Street", 1400L, 2831.32f));
//		col.add(new Product( 9L,"magazine","National Geographic","Arizona","Railway Station", 4000L, 38347f));
//		col.add(new Product( 9L,"magazine","National Geographic","Arizona","Baseball Stadium", 3000L, 9482.4f));
//		col.add(new Product( 9L,"magazine","National Geographic","Arizona","Shopping Center", 1500L, 8329.2f));
//		col.add(new Product( 10L,"food","snickers","Florida","Main Street", 2500L, 27475.5f));
//		col.add(new Product( 10L,"food","snickers","Florida","Railway Station", 1400L, 3322f));
//		col.add(new Product( 10L,"food","snickers","Florida","Baseball Stadium", 1500L, 78482f));
//		col.add(new Product( 10L,"food","snickers","Florida","Shopping Center", 2500L, 27475.5f));
		col.add(new Product( 10L,"food","snickers","New York","Main Street", 1400L, 3322f));
		col.add(new Product( 10L,"food","snickers","New York","Railway Station", 1500L, 78482f));
		col.add(new Product( 10L,"food","snickers","New York","Baseball Stadium", 2500L, 5831.32f));
//		col.add(new Product( 10L,"food","snickers","New York","Shopping Center", 1400L, 78482f));
//		col.add(new Product( 10L,"food","snickers","Washington","Main Street", 4000L, 2831.32f));
//		col.add(new Product( 10L,"food","snickers","Washington","Railway Station", 3000L, 38347f));
//		col.add(new Product( 10L,"food","snickers","Washington","Baseball Stadium", 4000L, 9482.4f));
//		col.add(new Product( 10L,"food","snickers","Washington","Shopping Center", 3000L, 8329.2f));
//		col.add(new Product( 10L,"food","snickers","Arizona","Main Street", 1500L, 27475.5f));
//		col.add(new Product( 10L,"food","snickers","Arizona","Railway Station", 8400L, 3322f));
//		col.add(new Product( 10L,"food","snickers","Arizona","Baseball Stadium", 1400L, 78482f));
//		col.add(new Product( 10L,"food","snickers","Arizona","Shopping Center", 4000L, 5831.32f));

		return col;
	}
	
	public static List getDummyCollectionSmall(){

		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("dd/MM/yyyy");

		List col =  new ArrayList();

		//The collection is ordered by State, Branch and Product Line
		col.add(new Product( 1L,"book","Harry Potter 7","Florida","Main Street", 250L, 10000f, true));
		col.add(new Product( 1L,"book","Harry Potter 7","Florida","Railway Station", 400L, 2831.32f, true));
		col.add(new Product( 1L,"book","Harry Potter 7","Florida","Baseball Stadium", 440L, null, false));
		col.add(new Product( 1L,"book","Harry Potter 7","Florida","Shopping Center", 300L, 9482.4f, false));
		col.add(new Product( 1L,"book","Harry Potter 7","New York","Main Street", 500L, 27475.5f, true));
		col.add(new Product( 1L,"book","Harry Potter 7","New York","Railway Station", 640L, 3322f, true));
		col.add(new Product( 1L,"book","Harry Potter 7","New York","Baseball Stadium", 100L, 78482f, false));
		col.add(new Product( 1L,"book","Harry Potter 7","New York","Shopping Center", 70L, 5831.32f, false));
		col.add(new Product( 3L,"book","The Pelican Brief,","Washington","Main Street",null, 3322f, true));
		col.add(new Product( 3L,"book","The Pelican Brief,","Washington","Railway Station", 98L, 78482f, true));
		col.add(new Product( 3L,"book","The Pelican Brief,","Washington","Baseball Stadium", 613L, 5831.32f, false));
		col.add(new Product( 3L,"book","The Pelican Brief,","Washington","Shopping Center", 87L, null, false));
		col.add(new Product( 3L,"book","The Pelican Brief,","Arizona","Main Street", 250L, 2831.32f, true));
		col.add(new Product( 3L,"book","The Pelican Brief,","Arizona","Railway Station", 550L, 38347f, false));

		return col;
	}

	public static List getDummyCollectionSmallVariation1(){

		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("dd/MM/yyyy");

		List col =  new ArrayList();

		//The collection is ordered by State, Branch and Product Line
		col.add(new Product( 1L,"book","Harry Potter 7","Florida","Main Street", 250L, 10000f, true));
		col.add(new Product( 2L,"dvd","Harry Potter 7","Florida","Railway Station", 400L, 2831.32f, true));
		col.add(new Product( 3L,"magazine","Harry Potter 7","Florida","Baseball Stadium", 440L, null, false));
		col.add(new Product( 4L,"book","Harry Potter 7","Florida","Shopping Center", 300L, 9482.4f, false));
		col.add(new Product( 5L,"dvd","Harry Potter 7","New York","Main Street", 500L, 27475.5f, true));
		col.add(new Product( 6L,"magazine","Harry Potter 7","New York","Railway Station", 640L, 3322f, true));
		col.add(new Product( 7L,"book","Harry Potter 7","New York","Baseball Stadium", 100L, 78482f, false));
		col.add(new Product( 8L,"dvd","Harry Potter 7","New York","Shopping Center", 70L, 5831.32f, false));
		col.add(new Product( 9L,"magazine","The Pelican Brief,","Washington","Main Street",null, 3322f, true));
		col.add(new Product( 10L,"book","The Pelican Brief,","Washington","Railway Station", 98L, 78482f, true));
		col.add(new Product( 11L,"dvd","The Pelican Brief,","Washington","Baseball Stadium", 613L, 5831.32f, false));
		col.add(new Product( 12L,"magazine","The Pelican Brief,","Washington","Shopping Center", 87L, null, false));
		col.add(new Product( 13L,"book","The Pelican Brief,","Arizona","Main Street", 250L, 2831.32f, true));
		col.add(new Product( 14L,"dvd","The Pelican Brief,","Arizona","Railway Station", 550L, 38347f, false));

		return col;
	}

	public static List getDummyCollectionSorted1(){
		List list = getDummyCollection();
		return SortUtils.sortCollection(list, new String[]{"state","branch","item"});
		
	}	
	
	public static void main(String[] args) {
		System.out.println(getDummyCollectionSorted1());
	}
}
