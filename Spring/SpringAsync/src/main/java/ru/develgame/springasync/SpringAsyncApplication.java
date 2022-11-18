package ru.develgame.springasync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@EnableAsync
@SpringBootApplication
public class SpringAsyncApplication {
	@Bean
	@Scope("prototype")
	public Worker getWorker() {
		return new Worker();
	}

	@Bean
	public WorkerManager getWorkerManager() {
		return new WorkerManager() {
			@Override
			protected Worker getNewWorker() {
				return getWorker();
			}
		};
	}

	@Bean(name = "threadPoolTestAsyncExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.initialize();
		return executor;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringAsyncApplication.class, args);

		long start = System.nanoTime();

		List<Future<Void>> jobs = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			jobs.add(context.getBean(WorkerManager.class).calculate());
		}

		while (jobs.stream().filter(it -> !it.isDone()).findAny().isPresent()) {
		}

		long finish = System.nanoTime();

		System.out.println("All time --- " + (finish - start) / 1000000000.0d + " sec");
	}
}
