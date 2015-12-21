package com.zengularity.raftology

sealed trait NodeState {
  def behaviour: Unit
}

case class Candidate() extends NodeState {
  def behaviour: Unit = {}
}

case class Follower() extends NodeState {
  def behaviour: Unit = {}
}

case class Leader() extends NodeState {
  def behaviour: Unit = {}
}