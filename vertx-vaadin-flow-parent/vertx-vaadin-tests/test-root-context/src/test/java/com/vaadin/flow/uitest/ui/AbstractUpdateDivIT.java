/*
 * Copyright 2000-2020 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.uitest.ui;

import com.vaadin.flow.testutil.ChromeBrowserTest;
import com.vaadin.testbench.TestBenchElement;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractUpdateDivIT extends ChromeBrowserTest {

    @Test
    public void pushUpdatesEmbeddedWebComponent() {
        open();
        int maxUpdates = AbstractPushUpdateDivView.MAX_UPDATE;

        int initialUpdateCount = getUpdateCount();
        Assert.assertTrue(
            "The initial update count should be less than maximum 50, but it has value "
                + initialUpdateCount,
            initialUpdateCount < maxUpdates);

        waitUntil(driver -> getUpdateCount() > initialUpdateCount, 5);

        int nextUpdateCount = getUpdateCount();

        Assert.assertTrue(
            "The next interim update count should be less than maximum 50, but it has value "
                + nextUpdateCount,
            nextUpdateCount < maxUpdates);

        waitUntil(driver -> getUpdateCount() == maxUpdates, 5);

        int updateCount = getUpdateCount();
        Assert.assertEquals(
            "The update count should have reached the maximin 50, but it has value "
                + updateCount,
            maxUpdates, updateCount);
    }

    private int getUpdateCount() {
        TestBenchElement div = $(TestBenchElement.class).id("push-update");
        String count = div.getText();
        return Integer.parseInt(count);
    }
}
