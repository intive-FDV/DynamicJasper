/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
