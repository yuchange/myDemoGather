/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.publish.ivy.internal.artifact;

import org.gradle.api.Action;
import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.DefaultDomainObjectSet;
import org.gradle.api.internal.file.FileCollectionFactory;
import org.gradle.api.internal.file.collections.MinimalFileSet;
import org.gradle.api.internal.tasks.AbstractTaskDependency;
import org.gradle.api.internal.tasks.TaskDependencyInternal;
import org.gradle.api.internal.tasks.TaskDependencyResolveContext;
import org.gradle.api.publish.ivy.IvyArtifact;
import org.gradle.api.publish.ivy.IvyArtifactSet;
import org.gradle.internal.typeconversion.NotationParser;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultIvyArtifactSet extends DefaultDomainObjectSet<IvyArtifact> implements IvyArtifactSet {
    private final String publicationName;
    private final TaskDependencyInternal builtBy = new ArtifactsTaskDependency();
    private final FileCollection files;
    private final NotationParser<Object, IvyArtifact> ivyArtifactParser;

    public DefaultIvyArtifactSet(String publicationName, NotationParser<Object, IvyArtifact> ivyArtifactParser, FileCollectionFactory fileCollectionFactory) {
        super(IvyArtifact.class);
        this.publicationName = publicationName;
        this.ivyArtifactParser = ivyArtifactParser;
        this.files = fileCollectionFactory.create(builtBy, new ArtifactsFileCollection());
    }

    public IvyArtifact artifact(Object source) {
        IvyArtifact artifact = ivyArtifactParser.parseNotation(source);
        add(artifact);
        return artifact;
    }

    public IvyArtifact artifact(Object source, Action<? super IvyArtifact> config) {
        IvyArtifact artifact = artifact(source);
        config.execute(artifact);
        return artifact;
    }

    public FileCollection getFiles() {
        return files;
    }

    private class ArtifactsFileCollection implements MinimalFileSet {
        @Override
        public String getDisplayName() {
            return String.format("artifacts for Ivy publication '%s'", publicationName);
        }

        @Override
        public Set<File> getFiles() {
            Set<File> files = new LinkedHashSet<File>();
            for (IvyArtifact artifact : DefaultIvyArtifactSet.this) {
                files.add(artifact.getFile());
            }
            return files;
        }
    }

    private class ArtifactsTaskDependency extends AbstractTaskDependency {
        @Override
        public void visitDependencies(TaskDependencyResolveContext context) {
            for (IvyArtifact ivyArtifact : DefaultIvyArtifactSet.this) {
                context.add(ivyArtifact);
            }
        }
    }
}