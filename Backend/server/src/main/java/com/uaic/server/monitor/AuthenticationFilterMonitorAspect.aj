package mop;
import com.uaic.server.security.JwtAuthenticationFilter;
import com.uaic.server.security.JwtUtil;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;

import java.lang.ref.*;
import org.aspectj.lang.*;

aspect BaseAspect {
	pointcut notwithin() :
	!within(sun..*) &&
	!within(java..*) &&
	!within(javax..*) &&
	!within(com.sun..*) &&
	!within(org.dacapo.harness..*) &&
	!within(org.apache.commons..*) &&
	!within(org.apache.geronimo..*) &&
	!within(net.sf.cglib..*) &&
	!within(mop..*) &&
	!within(javamoprt..*) &&
	!within(rvmonitorrt..*) &&
	!within(com.runtimeverification..*);
}

public aspect AuthenticationFilterMonitorAspect implements com.runtimeverification.rvmonitor.java.rt.RVMObject {
	public AuthenticationFilterMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock AuthenticationFilter_MOPLock = new ReentrantLock();
	static Condition AuthenticationFilter_MOPLock_cond = AuthenticationFilter_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(com.runtimeverification.rvmonitor.java.rt.RVMObject+) && !adviceexecution() && BaseAspect.notwithin();
	pointcut AuthenticationFilter_extracttoken(JwtAuthenticationFilter filter) : (call(* JwtAuthenticationFilter.extractToken(..)) && target(filter)) && MOP_CommonPointCut();
	after (JwtAuthenticationFilter filter) : AuthenticationFilter_extracttoken(filter) {
		AuthenticationFilterRuntimeMonitor.extracttokenEvent(filter);
	}

	pointcut AuthenticationFilter_validatetoken(JwtUtil util) : (call(* JwtUtil.validateToken(..)) && target(util)) && MOP_CommonPointCut();
	after (JwtUtil util) : AuthenticationFilter_validatetoken(util) {
		AuthenticationFilterRuntimeMonitor.validatetokenEvent(util);
	}

	pointcut AuthenticationFilter_extractclaims(JwtUtil util) : (call(* JwtUtil.extractClaims(..)) && target(util)) && MOP_CommonPointCut();
	after (JwtUtil util) : AuthenticationFilter_extractclaims(util) {
		AuthenticationFilterRuntimeMonitor.extractclaimsEvent(util);
	}

	pointcut AuthenticationFilter_createuserfromclaims(JwtAuthenticationFilter filter) : (call(* JwtAuthenticationFilter.createUserFromClaims(..)) && target(filter)) && MOP_CommonPointCut();
	after (JwtAuthenticationFilter filter) : AuthenticationFilter_createuserfromclaims(filter) {
		AuthenticationFilterRuntimeMonitor.createuserfromclaimsEvent(filter);
	}

	pointcut AuthenticationFilter_createaccesstoken(JwtUtil util) : (call(* JwtUtil.createAccessToken(..)) && target(util)) && MOP_CommonPointCut();
	after (JwtUtil util) : AuthenticationFilter_createaccesstoken(util) {
		AuthenticationFilterRuntimeMonitor.createaccesstokenEvent(util);
	}

	pointcut AuthenticationFilter_sendunauthorizederror(JwtAuthenticationFilter filter) : (call(* JwtAuthenticationFilter.sendUnauthorizedError(..)) && target(filter)) && MOP_CommonPointCut();
	after (JwtAuthenticationFilter filter) : AuthenticationFilter_sendunauthorizederror(filter) {
		AuthenticationFilterRuntimeMonitor.sendunauthorizederrorEvent(filter);
	}

}
