/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dexecutor.executor;

import static com.github.dexecutor.executor.support.Preconditions.checkNotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.github.dexecutor.executor.graph.Graph.Node;
/**
 * Default Executor, which internally operates on @ExecutorService
 * 
 * @author Nadeem Mohammad
 *
 * @param <T>
 * @param <R>
 */
public final class DefaultExecutionEngine<T, R> implements ExecutionEngine<T, R> {

	private final CompletionService<Node<T, R>> completionService;
	private final ExecutorService executorService;
	/**
	 * Creates the default instance given @ExecutorService, internally it uses @CompletionService
	 * @param executorService
	 */
	public DefaultExecutionEngine(final ExecutorService executorService) {
		checkNotNull(executorService, "Executer Service should not be null");
		this.executorService = executorService;
		this.completionService = new ExecutorCompletionService<Node<T, R>>(executorService);
	}

	@Override
	public Future<Node<T, R>> submit(Callable<Node<T, R>> task) {
		return this.completionService.submit(task);
	}

	@Override
	public Future<Node<T, R>> take() throws InterruptedException {
		return this.completionService.take();
	}

	@Override
	public boolean isShutdown() {
		return this.executorService.isShutdown();
	}

	@Override
	public String toString() {
		return this.executorService.toString();
	}
}