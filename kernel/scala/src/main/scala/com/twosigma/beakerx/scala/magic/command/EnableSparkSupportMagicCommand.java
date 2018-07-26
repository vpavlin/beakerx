/*
 *  Copyright 2018 TWO SIGMA OPEN SOURCE, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.twosigma.beakerx.scala.magic.command;

import com.twosigma.beakerx.kernel.KernelFunctionality;
import com.twosigma.beakerx.kernel.magic.command.MagicCommandExecutionParam;
import com.twosigma.beakerx.kernel.magic.command.MagicCommandFunctionality;
import com.twosigma.beakerx.kernel.magic.command.outcome.MagicCommandOutcomeItem;

public class EnableSparkSupportMagicCommand implements MagicCommandFunctionality {

  public static final String ENABLE_SPARK_SUPPORT = "%%spark";
  private EnableSparkSupportMagicConfiguration command;
  private KernelFunctionality kernel;

  public EnableSparkSupportMagicCommand(KernelFunctionality kernel, SparkInitCommandFactory initCommandFactory) {
    this.kernel = kernel;
    this.command = new EnableSparkSupportMagicInitConfiguration(initCommandFactory);
  }

  @Override
  public String getMagicCommandName() {
    return ENABLE_SPARK_SUPPORT;
  }

  @Override
  public MagicCommandOutcomeItem execute(MagicCommandExecutionParam param) {
    if (command.isInit()) {
      return initSparkSupport(param);
    } else {
      return command.run(param);
    }
  }

  private MagicCommandOutcomeItem initSparkSupport(MagicCommandExecutionParam param) {
    MagicCommandOutcomeItem init = command.run(param);
    if (init.getStatus().equals(MagicCommandOutcomeItem.Status.OK)) {
      this.command = new EnableSparkSupportMagicSparkConfiguration(this.kernel);
      return this.command.run(param);
    } else {
      return init;
    }
  }

  interface EnableSparkSupportMagicConfiguration {
    MagicCommandOutcomeItem run(MagicCommandExecutionParam param);

    boolean isInit();
  }
}
