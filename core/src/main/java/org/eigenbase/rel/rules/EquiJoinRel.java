/*
// Licensed to the Apache Software Foundation (ASF) under one or more
// contributor license agreements.  See the NOTICE file distributed with
// this work for additional information regarding copyright ownership.
// The ASF licenses this file to you under the Apache License, Version 2.0
// (the "License"); you may not use this file except in compliance with
// the License.  You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package org.eigenbase.rel.rules;

import java.util.Set;

import org.eigenbase.rel.JoinInfo;
import org.eigenbase.rel.JoinRelBase;
import org.eigenbase.rel.JoinRelType;
import org.eigenbase.rel.RelNode;
import org.eigenbase.relopt.RelOptCluster;
import org.eigenbase.relopt.RelTraitSet;
import org.eigenbase.rex.RexNode;
import org.eigenbase.util.ImmutableIntList;

import com.google.common.base.Preconditions;

/**
 * Base class for any join whose condition is based on column equality.
 */
public abstract class EquiJoinRel extends JoinRelBase {
  public final ImmutableIntList leftKeys;
  public final ImmutableIntList rightKeys;

  /** Creates an EquiJoinRel. */
  public EquiJoinRel(RelOptCluster cluster, RelTraitSet traits, RelNode left,
      RelNode right, RexNode condition, ImmutableIntList leftKeys,
      ImmutableIntList rightKeys, JoinRelType joinType,
      Set<String> variablesStopped) {
    super(cluster, traits, left, right, condition, joinType, variablesStopped);
    this.leftKeys = Preconditions.checkNotNull(leftKeys);
    this.rightKeys = Preconditions.checkNotNull(rightKeys);
  }

  public ImmutableIntList getLeftKeys() {
    return leftKeys;
  }

  public ImmutableIntList getRightKeys() {
    return rightKeys;
  }

  @Override
  public JoinInfo analyzeCondition() {
    return new JoinInfo(leftKeys, rightKeys,
        getCluster().getRexBuilder().makeLiteral(true));
  }
}

// End EquiJoinRel.java