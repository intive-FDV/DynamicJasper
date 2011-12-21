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
package ar.com.fdvs.dj.domain;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class DJCrosstabTest extends TestCase {

    public void testGetVisibleMeasures() throws Exception {
        DJCrosstabMeasure visibleMeasure1 = new DJCrosstabMeasure();
        visibleMeasure1.setVisible(Boolean.TRUE);

        DJCrosstabMeasure visibleMeasure2 = new DJCrosstabMeasure();
        visibleMeasure2.setVisible(Boolean.TRUE);

        DJCrosstabMeasure invisibleMeasure = new DJCrosstabMeasure();
        invisibleMeasure.setVisible(Boolean.FALSE);


        //first case: two visibles and one invisible
        List<DJCrosstabMeasure> visibleAndInvisible = new ArrayList<DJCrosstabMeasure>();

        visibleAndInvisible.add(visibleMeasure1);
        visibleAndInvisible.add(visibleMeasure2);
        visibleAndInvisible.add(invisibleMeasure);

        DJCrosstab crosstab = new DJCrosstab();
        crosstab.setMeasures(visibleAndInvisible);

        Assert.assertNotNull(crosstab.getVisibleMeasures());
        Assert.assertFalse(crosstab.getVisibleMeasures().isEmpty());
        Assert.assertEquals(2, crosstab.getVisibleMeasures().size());

        //second case: two visibles
        List<DJCrosstabMeasure> visibleOnly = new ArrayList<DJCrosstabMeasure>();

        visibleOnly.add(visibleMeasure1);
        visibleOnly.add(visibleMeasure2);
        visibleOnly.add(invisibleMeasure);

        crosstab.setMeasures(visibleOnly);

        Assert.assertNotNull(crosstab.getVisibleMeasures());
        Assert.assertFalse(crosstab.getVisibleMeasures().isEmpty());
        Assert.assertEquals(2, crosstab.getVisibleMeasures().size());

        //third case: only one invisible
        List<DJCrosstabMeasure> invisibleOnly = new ArrayList<DJCrosstabMeasure>();
        invisibleOnly.add(invisibleMeasure);

        crosstab.setMeasures(invisibleOnly);

        Assert.assertNotNull(crosstab.getVisibleMeasures());
        Assert.assertTrue(crosstab.getVisibleMeasures().isEmpty());


    }
}
