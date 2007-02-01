/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2007  FDV Solutions (http://www.fdvsolutions.com)
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ar.com.fdvs.dj.test.domain.Product;

public class TestRepositoryProducts {

	public static Collection getDummyCollection(){

		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("dd/MM/yyyy");

		List col =  new ArrayList();

		//The collection is ordered by State, Branch and Product Line
		col.add(new Product( new Long("2"),"vacuno","carne picada","Buenos Aires","9 de julio", new Long("2500"), new Float("78482")));
		col.add(new Product( new Long("3"),"vacuno","costeleta","Buenos Aires","9 de julio", new Long("1400"), new Float("2831.32")));
		col.add(new Product( new Long("4"),"vacuno","achura","Buenos Aires","9 de julio", new Long("4000"), new Float("38347")));
		col.add(new Product( new Long("5"),"vacuno","vacio","Buenos Aires","9 de julio", new Long("3000"), new Float("9482.4")));
		col.add(new Product( new Long("6"),"Porcino","costeleta","Buenos Aires","9 de julio", new Long("1500"), new Float("8329.2")));
		col.add(new Product( new Long("7"),"Porcino","Costeleta","Buenos Aires","9 de julio", new Long("2500"), new Float("27475.5")));
		col.add(new Product( new Long("8"),"Porcino","Matambre","Buenos Aires","9 de julio", new Long("1400"), new Float("3322")));
		col.add(new Product( new Long("9"),"Avicola","Pollo","Buenos Aires","9 de julio", new Long("4000"), new Float("78482")));
		col.add(new Product( new Long("10"),"Avicola","pechuga pollo","Buenos Aires","9 de julio", new Long("3000"), new Float("5831.32")));
		col.add(new Product( new Long("11"),"Avicola","pata muslo pollo","Buenos Aires","9 de julio", new Long("1500"), new Float("78482")));
		col.add(new Product( new Long("12"),"lacteo","yogurt","Buenos Aires","9 de julio", new Long("8400"), new Float("2831.32")));
		col.add(new Product( new Long("13"),"lacteo","leche","Buenos Aires","9 de julio", new Long("1400"), new Float("38347")));
		col.add(new Product( new Long("14"),"lacteo","queso","Buenos Aires","9 de julio", new Long("4000"), new Float("9482.4")));
		col.add(new Product( new Long("15"),"lacteo","dulce de leche","Buenos Aires","9 de julio", new Long("3000"), new Float("8329.2")));
		col.add(new Product( new Long("1"),"vacuno","asado","Buenos Aires","9 de julio", new Long("1500"), new Float("27475.5")));
		col.add(new Product( new Long("9"),"Avicola","Pollo","Buenos Aires","Belgrano", new Long("4000"), new Float("3322")));
		col.add(new Product( new Long("10"),"Avicola","pechuga pollo","Buenos Aires","Belgrano", new Long("3000"), new Float("78482")));
		col.add(new Product( new Long("11"),"Avicola","pata muslo pollo","Buenos Aires","Belgrano", new Long("1500"), new Float("5831.32")));
		col.add(new Product( new Long("12"),"lacteo","yogurt","Buenos Aires","Belgrano", new Long("8400"), new Float("78482")));
		col.add(new Product( new Long("13"),"lacteo","leche","Buenos Aires","Belgrano", new Long("1400"), new Float("2831.32")));
		col.add(new Product( new Long("14"),"lacteo","queso","Buenos Aires","Belgrano", new Long("4000"), new Float("38347")));
		col.add(new Product( new Long("15"),"lacteo","dulce de leche","Buenos Aires","Belgrano", new Long("3000"), new Float("9482.4")));
		col.add(new Product( new Long("6"),"Porcino","costeleta","Buenos Aires","Belgrano", new Long("1500"), new Float("8329.2")));
		col.add(new Product( new Long("7"),"Porcino","Costeleta","Buenos Aires","Belgrano", new Long("2500"), new Float("27475.5")));
		col.add(new Product( new Long("8"),"Porcino","Matambre","Buenos Aires","Belgrano", new Long("1400"), new Float("3322")));
		col.add(new Product( new Long("1"),"vacuno","asado","Buenos Aires","Belgrano", new Long("1500"), new Float("78482")));
		col.add(new Product( new Long("2"),"vacuno","carne picada","Buenos Aires","Belgrano", new Long("2500"), new Float("5831.32")));
		col.add(new Product( new Long("3"),"vacuno","costeleta","Buenos Aires","Belgrano", new Long("1400"), new Float("78482")));
		col.add(new Product( new Long("4"),"vacuno","achura","Buenos Aires","Belgrano", new Long("4000"), new Float("2831.32")));
		col.add(new Product( new Long("5"),"vacuno","vacio","Buenos Aires","Belgrano", new Long("3000"), new Float("38347")));
		col.add(new Product( new Long("9"),"Avicola","Pollo","Buenos Aires","Palermo", new Long("4000"), new Float("9482.4")));
		col.add(new Product( new Long("10"),"Avicola","pechuga pollo","Buenos Aires","Palermo", new Long("3000"), new Float("8329.2")));
		col.add(new Product( new Long("11"),"Avicola","pata muslo pollo","Buenos Aires","Palermo", new Long("1500"), new Float("27475.5")));
		col.add(new Product( new Long("12"),"lacteo","yogurt","Buenos Aires","Palermo", new Long("8400"), new Float("3322")));
		col.add(new Product( new Long("13"),"lacteo","leche","Buenos Aires","Palermo", new Long("1400"), new Float("78482")));
		col.add(new Product( new Long("14"),"lacteo","queso","Buenos Aires","Palermo", new Long("4000"), new Float("5831.32")));
		col.add(new Product( new Long("15"),"lacteo","dulce de leche","Buenos Aires","Palermo", new Long("3000"), new Float("78482")));
		col.add(new Product( new Long("6"),"Porcino","costeleta","Buenos Aires","Palermo", new Long("1500"), new Float("2831.32")));
		col.add(new Product( new Long("7"),"Porcino","Costeleta","Buenos Aires","Palermo", new Long("2500"), new Float("38347")));
		col.add(new Product( new Long("8"),"Porcino","Matambre","Buenos Aires","Palermo", new Long("1400"), new Float("9482.4")));
		col.add(new Product( new Long("1"),"vacuno","asado","Buenos Aires","Palermo", new Long("1500"), new Float("8329.2")));
		col.add(new Product( new Long("2"),"vacuno","carne picada","Buenos Aires","Palermo", new Long("2500"), new Float("27475.5")));
		col.add(new Product( new Long("3"),"vacuno","costeleta","Buenos Aires","Palermo", new Long("1400"), new Float("3322")));
		col.add(new Product( new Long("4"),"vacuno","achura","Buenos Aires","Palermo", new Long("4000"), new Float("78482")));
		col.add(new Product( new Long("5"),"vacuno","vacio","Buenos Aires","Palermo", new Long("3000"), new Float("5831.32")));
		col.add(new Product( new Long("9"),"Avicola","Pollo","Cordoba","Cordoba", new Long("4000"), new Float("78482")));
		col.add(new Product( new Long("10"),"Avicola","pechuga pollo","Cordoba","Cordoba", new Long("3000"), new Float("2831.32")));
		col.add(new Product( new Long("11"),"Avicola","pata muslo pollo","Cordoba","Cordoba", new Long("1500"), new Float("38347")));
		col.add(new Product( new Long("12"),"lacteo","yogurt","Cordoba","Cordoba", new Long("8400"), new Float("9482.4")));
		col.add(new Product( new Long("13"),"lacteo","leche","Cordoba","Cordoba", new Long("1400"), new Float("8329.2")));
		col.add(new Product( new Long("14"),"lacteo","queso","Cordoba","Cordoba", new Long("4000"), new Float("27475.5")));
		col.add(new Product( new Long("15"),"lacteo","dulce de leche","Cordoba","Cordoba", new Long("3000"), new Float("3322")));
		col.add(new Product( new Long("6"),"Porcino","costeleta","Cordoba","Cordoba", new Long("1500"), new Float("78482")));
		col.add(new Product( new Long("7"),"Porcino","Costeleta","Cordoba","Cordoba", new Long("2500"), new Float("5831.32")));
		col.add(new Product( new Long("8"),"Porcino","Matambre","Cordoba","Cordoba", new Long("1400"), new Float("78482")));
		col.add(new Product( new Long("1"),"vacuno","asado","Cordoba","Cordoba", new Long("1500"), new Float("2831.32")));
		col.add(new Product( new Long("2"),"vacuno","carne picada","Cordoba","Cordoba", new Long("2500"), new Float("38347")));
		col.add(new Product( new Long("3"),"vacuno","costeleta","Cordoba","Cordoba", new Long("1400"), new Float("9482.4")));
		col.add(new Product( new Long("4"),"vacuno","achura","Cordoba","Cordoba", new Long("4000"), new Float("8329.2")));
		col.add(new Product( new Long("5"),"vacuno","vacio","Cordoba","Cordoba", new Long("3000"), new Float("27475.5")));
		col.add(new Product( new Long("9"),"Avicola","Pollo","Cordoba","Villa general Belgrano", new Long("4000"), new Float("3322")));
		col.add(new Product( new Long("10"),"Avicola","pechuga pollo","Cordoba","Villa general Belgrano", new Long("3000"), new Float("78482")));
		col.add(new Product( new Long("11"),"Avicola","pata muslo pollo","Cordoba","Villa general Belgrano", new Long("1500"), new Float("5831.32")));
		col.add(new Product( new Long("12"),"lacteo","yogurt","Cordoba","Villa general Belgrano", new Long("8400"), new Float("3322")));
		col.add(new Product( new Long("13"),"lacteo","leche","Cordoba","Villa general Belgrano", new Long("1400"), new Float("78482")));
		col.add(new Product( new Long("14"),"lacteo","queso","Cordoba","Villa general Belgrano", new Long("4000"), new Float("5831.32")));
		col.add(new Product( new Long("15"),"lacteo","dulce de leche","Cordoba","Villa general Belgrano", new Long("3000"), new Float("78482")));
		col.add(new Product( new Long("6"),"Porcino","costeleta","Cordoba","Villa general Belgrano", new Long("1500"), new Float("2831.32")));
		col.add(new Product( new Long("7"),"Porcino","Costeleta","Cordoba","Villa general Belgrano", new Long("2500"), new Float("38347")));
		col.add(new Product( new Long("8"),"Porcino","Matambre","Cordoba","Villa general Belgrano", new Long("1400"), new Float("9482.4")));
		col.add(new Product( new Long("1"),"vacuno","asado","Cordoba","Villa general Belgrano", new Long("1500"), new Float("8329.2")));
		col.add(new Product( new Long("2"),"vacuno","carne picada","Cordoba","Villa general Belgrano", new Long("2500"), new Float("27475.5")));
		col.add(new Product( new Long("3"),"vacuno","costeleta","Cordoba","Villa general Belgrano", new Long("1400"), new Float("3322")));
		col.add(new Product( new Long("4"),"vacuno","achura","Cordoba","Villa general Belgrano", new Long("4000"), new Float("78482")));
		col.add(new Product( new Long("5"),"vacuno","vacio","Cordoba","Villa general Belgrano", new Long("3000"), new Float("5831.32")));
		col.add(new Product( new Long("9"),"Avicola","Pollo","Mendoza","Mendoza", new Long("4000"), new Float("78482")));
		col.add(new Product( new Long("10"),"Avicola","pechuga pollo","Mendoza","Mendoza", new Long("3000"), new Float("2831.32")));
		col.add(new Product( new Long("11"),"Avicola","pata muslo pollo","Mendoza","Mendoza", new Long("1500"), new Float("38347")));
		col.add(new Product( new Long("12"),"lacteo","yogurt","Mendoza","Mendoza", new Long("8400"), new Float("9482.4")));
		col.add(new Product( new Long("13"),"lacteo","leche","Mendoza","Mendoza", new Long("1400"), new Float("8329.2")));
		col.add(new Product( new Long("14"),"lacteo","queso","Mendoza","Mendoza", new Long("4000"), new Float("27475.5")));
		col.add(new Product( new Long("15"),"lacteo","dulce de leche","Mendoza","Mendoza", new Long("3000"), new Float("3322")));
		col.add(new Product( new Long("6"),"Porcino","costeleta","Mendoza","Mendoza", new Long("1500"), new Float("78482")));
		col.add(new Product( new Long("7"),"Porcino","Costeleta","Mendoza","Mendoza", new Long("2500"), new Float("5831.32")));
		col.add(new Product( new Long("8"),"Porcino","Matambre","Mendoza","Mendoza", new Long("1400"), new Float("3322")));
		col.add(new Product( new Long("1"),"vacuno","asado","Mendoza","Mendoza", new Long("1500"), new Float("78482")));
		col.add(new Product( new Long("2"),"vacuno","carne picada","Mendoza","Mendoza", new Long("2500"), new Float("5831.32")));
		col.add(new Product( new Long("3"),"vacuno","costeleta","Mendoza","Mendoza", new Long("1400"), new Float("78482")));
		col.add(new Product( new Long("4"),"vacuno","achura","Mendoza","Mendoza", new Long("4000"), new Float("2831.32")));
		col.add(new Product( new Long("5"),"vacuno","vacio","Mendoza","Mendoza", new Long("3000"), new Float("38347")));
		col.add(new Product( new Long("9"),"Avicola","Pollo","Misiones","Posadas", new Long("4000"), new Float("9482.4")));
		col.add(new Product( new Long("10"),"Avicola","pechuga pollo","Misiones","Posadas", new Long("3000"), new Float("8329.2")));
		col.add(new Product( new Long("11"),"Avicola","pata muslo pollo","Misiones","Posadas", new Long("1500"), new Float("27475.5")));
		col.add(new Product( new Long("12"),"lacteo","yogurt","Misiones","Posadas", new Long("8400"), new Float("3322")));
		col.add(new Product( new Long("13"),"lacteo","leche","Misiones","Posadas", new Long("1400"), new Float("78482")));
		col.add(new Product( new Long("14"),"lacteo","queso","Misiones","Posadas", new Long("4000"), new Float("5831.32")));
		col.add(new Product( new Long("15"),"lacteo","dulce de leche","Misiones","Posadas", new Long("3000"), new Float("78482")));
		col.add(new Product( new Long("6"),"Porcino","costeleta","Misiones","Posadas", new Long("1500"), new Float("2831.32")));
		col.add(new Product( new Long("7"),"Porcino","Costeleta","Misiones","Posadas", new Long("2500"), new Float("38347")));
		col.add(new Product( new Long("8"),"Porcino","Matambre","Misiones","Posadas", new Long("1400"), new Float("9482.4")));
		col.add(new Product( new Long("1"),"vacuno","asado","Misiones","Posadas", new Long("1500"), new Float("8329.2")));
		col.add(new Product( new Long("2"),"vacuno","carne picada","Misiones","Posadas", new Long("2500"), new Float("27475.5")));
		col.add(new Product( new Long("3"),"vacuno","costeleta","Misiones","Posadas", new Long("1400"), new Float("3322")));
		col.add(new Product( new Long("4"),"vacuno","achura","Misiones","Posadas", new Long("4000"), new Float("78482")));
		col.add(new Product( new Long("5"),"vacuno","vacio","Misiones","Posadas", new Long("3000"), new Float("5831.32")));
		col.add(new Product( new Long("9"),"Avicola","Pollo","Santa Fe","Rosario", new Long("4000"), new Float("3322")));
		col.add(new Product( new Long("10"),"Avicola","pechuga pollo","Santa Fe","Rosario", new Long("3000"), new Float("78482")));
		col.add(new Product( new Long("11"),"Avicola","pata muslo pollo","Santa Fe","Rosario", new Long("1500"), new Float("5831.32")));
		col.add(new Product( new Long("12"),"lacteo","yogurt","Santa Fe","Rosario", new Long("8400"), new Float("78482")));
		col.add(new Product( new Long("13"),"lacteo","leche","Santa Fe","Rosario", new Long("1400"), new Float("2831.32")));
		col.add(new Product( new Long("14"),"lacteo","queso","Santa Fe","Rosario", new Long("4000"), new Float("38347")));
		col.add(new Product( new Long("15"),"lacteo","dulce de leche","Santa Fe","Rosario", new Long("3000"), new Float("9482.4")));
		col.add(new Product( new Long("6"),"Porcino","costeleta","Santa Fe","Rosario", new Long("1500"), new Float("8329.2")));
		col.add(new Product( new Long("7"),"Porcino","Costeleta","Santa Fe","Rosario", new Long("2500"), new Float("27475.5")));
		col.add(new Product( new Long("8"),"Porcino","Matambre","Santa Fe","Rosario", new Long("1400"), new Float("3322")));
		col.add(new Product( new Long("1"),"vacuno","asado","Santa Fe","Rosario", new Long("1500"), new Float("78482")));
		col.add(new Product( new Long("2"),"vacuno","carne picada","Santa Fe","Rosario", new Long("2500"), new Float("5831.32")));
		col.add(new Product( new Long("3"),"vacuno","costeleta","Santa Fe","Rosario", new Long("1400"), new Float("78482")));
		col.add(new Product( new Long("4"),"vacuno","achura","Santa Fe","Rosario", new Long("4000"), new Float("2831.32")));
		col.add(new Product( new Long("5"),"vacuno","vacio","Santa Fe","Rosario", new Long("3000"), new Float("38347")));
		col.add(new Product( new Long("9"),"Avicola","Pollo","Santa Fe","Santa Fe", new Long("4000"), new Float("9482.4")));
		col.add(new Product( new Long("10"),"Avicola","pechuga pollo","Santa Fe","Santa Fe", new Long("3000"), new Float("8329.2")));
		col.add(new Product( new Long("11"),"Avicola","pata muslo pollo","Santa Fe","Santa Fe", new Long("1500"), new Float("27475.5")));
		col.add(new Product( new Long("12"),"lacteo","yogurt","Santa Fe","Santa Fe", new Long("8400"), new Float("3322")));
		col.add(new Product( new Long("13"),"lacteo","leche","Santa Fe","Santa Fe", new Long("1400"), new Float("78482")));
		col.add(new Product( new Long("14"),"lacteo","queso","Santa Fe","Santa Fe", new Long("4000"), new Float("5831.32")));
		col.add(new Product( new Long("15"),"lacteo","dulce de leche","Santa Fe","Santa Fe", new Long("3000"), new Float("3322")));
		col.add(new Product( new Long("6"),"Porcino","costeleta","Santa Fe","Santa Fe", new Long("1500"), new Float("78482")));
		col.add(new Product( new Long("7"),"Porcino","Costeleta","Santa Fe","Santa Fe", new Long("2500"), new Float("5831.32")));
		col.add(new Product( new Long("8"),"Porcino","Matambre","Santa Fe","Santa Fe", new Long("1400"), new Float("78482")));
		col.add(new Product( new Long("1"),"vacuno","asado","Santa Fe","Santa Fe", new Long("1500"), new Float("2831.32")));
		col.add(new Product( new Long("2"),"vacuno","carne picada","Santa Fe","Santa Fe", new Long("2500"), new Float("38347")));
		col.add(new Product( new Long("3"),"vacuno","costeleta","Santa Fe","Santa Fe", new Long("1400"), new Float("9482.4")));
		col.add(new Product( new Long("4"),"vacuno","achura","Santa Fe","Santa Fe", new Long("4000"), new Float("8329.2")));
		col.add(new Product( new Long("5"),"vacuno","vacio","Santa Fe","Santa Fe", new Long("3000"), new Float("27475.5")));

		return col;
	}
}
