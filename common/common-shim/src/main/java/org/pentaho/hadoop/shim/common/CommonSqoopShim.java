/*******************************************************************************
 *
 * Pentaho Big Data
 *
 * Copyright (C) 2002-2018 by Hitachi Vantara : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.hadoop.shim.common;

import org.pentaho.hadoop.shim.ShimVersion;
import org.pentaho.hadoop.shim.api.Configuration;
import org.pentaho.hadoop.shim.spi.SqoopShim;

//#if shim_type=="HDP" || shim_type=="EMR" || shim_type=="HDI" || shim_name=="mapr60" || shim_name=="cdh601"
import org.apache.sqoop.Sqoop;
//#endif
//#if shim_type=="CDH" && shim_name!="cdh601" || shim_type=="MAPR" && shim_name!="mapr60"
//$import com.cloudera.sqoop.Sqoop;
//#endif

@SuppressWarnings( "deprecation" )
public class CommonSqoopShim implements SqoopShim {

  @Override
  public ShimVersion getVersion() {
    return new ShimVersion( 1, 0 );
  }

  @Override
  public int runTool( String[] args, Configuration c ) {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
    try {
      return Sqoop.runTool( args, ShimUtils.asConfiguration( c ) );
    } finally {
      Thread.currentThread().setContextClassLoader( cl );
    }
  }

}
