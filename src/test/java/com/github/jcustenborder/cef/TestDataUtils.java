/**
 * Copyright © 2017 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.cef;

import com.google.common.io.Files;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestDataUtils {
  private static final Logger log = LoggerFactory.getLogger(TestDataUtils.class);

  public static <T extends NamedTest> List<T> loadJsonResourceFiles(String packageName, Class<T> cls) throws IOException {
//    Preconditions.checkNotNull(packageName, "packageName cannot be null");
    Reflections reflections = new Reflections(packageName, new ResourcesScanner());
    Set<String> resources = reflections.getResources(new FilterBuilder.Include(".*"));
    List<T> datas = new ArrayList<>(resources.size());
    Path packagePath = Paths.get("/" + packageName.replace(".", "/"));
    for (String resource : resources) {
      log.trace("Loading resource {}", resource);
      Path resourcePath = Paths.get("/" + resource);
      Path relativePath = packagePath.relativize(resourcePath);
      File resourceFile = new File("/" + resource);
      T data;
      try (InputStream inputStream = cls.getResourceAsStream(resourceFile.getAbsolutePath())) {
        data = ObjectMapperFactory.INSTANCE.readValue(inputStream, cls);
      } catch (IOException ex) {
        if (log.isErrorEnabled()) {
          log.error("Exception thrown while loading {}", resourcePath, ex);
        }
        throw ex;
      }
      String nameWithoutExtension = Files.getNameWithoutExtension(resource);
      if (null != relativePath.getParent()) {
        String parentName = relativePath.getParent().getFileName().toString();
        data.testName(parentName + "/" + nameWithoutExtension);
      } else {
        data.testName(nameWithoutExtension);
      }
      datas.add(data);
    }
    return datas;
  }
}
