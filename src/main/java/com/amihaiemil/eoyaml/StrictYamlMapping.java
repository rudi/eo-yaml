/**
 * Copyright (c) 2016-2020, Mihai Emil Andronache
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * Neither the name of the copyright holder nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package com.amihaiemil.eoyaml;

import java.util.Collection;
import java.util.Set;

/**
 * Decorator for a {@link YamlMapping} which throws
 * YamlNodenotFoundException, instead of returning null,
 * if a given key doesn't exist in the mapping, or if it points
 * to a different type of node than the demanded one.<br><br>
 * It is based on the fail-fast and null-is-bad idea <br>
 * see here: http://www.yegor256.com/2014/05/13/why-null-is-bad.html
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 1.0.0
 */
public final class StrictYamlMapping extends BaseYamlMapping {

    /**
     * Original YamlMapping.
     */
    private YamlMapping decorated;

    /**
     * Ctor.
     * @param decorated Original YamlMapping
     */
    public StrictYamlMapping(final YamlMapping decorated) {
        this.decorated = decorated;
    }

    @Override
    public Collection<YamlNode> values() {
        return this.decorated.values();
    }

    @Override
    public YamlMapping yamlMapping(final String key) {
        return this.yamlMapping(new PlainStringScalar(key));
    }

    @Override
    public YamlMapping yamlMapping(final YamlNode key) {
        YamlMapping found = this.decorated.yamlMapping(key);
        if (found == null) {
            throw new YamlNodeNotFoundException(
                "No YamlMapping found for key " + key
            );
        }
        return found;
    }

    @Override
    public YamlSequence yamlSequence(final String key) {
        return this.yamlSequence(new PlainStringScalar(key));
    }

    @Override
    public YamlSequence yamlSequence(final YamlNode key) {
        YamlSequence found = this.decorated.yamlSequence(key);
        if (found == null) {
            throw new YamlNodeNotFoundException(
                "No YamlSequence found for key " + key
            );
        }
        return found;
    }

    @Override
    public String string(final String key) {
        return this.string(new PlainStringScalar(key));
    }

    @Override
    public String string(final YamlNode key) {
        String found = this.decorated.string(key);
        if (found == null) {
            throw new YamlNodeNotFoundException(
                "No String found for key " + key
            );
        }
        return found;
    }

    @Override
    public String foldedBlockScalar(final String key) {
        return this.foldedBlockScalar(new PlainStringScalar(key));
    }

    @Override
    public String foldedBlockScalar(final YamlNode key) {
        final String found = this.decorated.foldedBlockScalar(key);
        if (found == null) {
            throw new YamlNodeNotFoundException(
                "No Folded Block Scalar found for key " + key
            );
        }
        return found;
    }

    @Override
    public Set<YamlNode> keys() {
        return this.decorated.keys();
    }

    @Override
    public YamlNode value(final YamlNode key) {
        YamlNode found = this.decorated.value(key);
        if (found == null) {
            throw new YamlNodeNotFoundException(
                "No String found for key " + key
            );
        }
        return found;
    }
}
