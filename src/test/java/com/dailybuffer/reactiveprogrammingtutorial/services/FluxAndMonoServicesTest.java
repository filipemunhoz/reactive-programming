package com.dailybuffer.reactiveprogrammingtutorial.services;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoServicesTest {

	FluxAndMonoServices c = new FluxAndMonoServices();
	
	@Test
	void fluxTest() {		
		Flux<String> flux = c.printFlux();
		
		StepVerifier.create(flux)
		.expectNext("Iron", "Metallica", "Megadeth", "Asia")
		.verifyComplete();		
	}
	
	@Test
	void fluxMapTest() {		
		var fluxMap = c.printFluxMap();
		
		StepVerifier.create(fluxMap)
			.expectNext("IRON", "METALLICA", "MEGADETH", "ASIA")
			.verifyComplete();
	}

	@Test
	void fluxFlatMapTest() {		
		var fluxMap = c.printFlatMap();
		
		StepVerifier.create(fluxMap)
			.expectNextCount(25)
			.verifyComplete();
	}
	
	@Test
	public void fluxFlatMapAsync() {
		var fluxMap = c.printFlatMapAsync();
		
		StepVerifier.create(fluxMap)
			.expectNextCount(25)
			.verifyComplete();
	}
	
	@Test
	void printFlatConcatMap() {
		var fluxMap = c.printFlatConcatMap();
		
		StepVerifier.create(fluxMap)
			.expectNextCount(25)
			.verifyComplete();		
	}
	
	@Test
	void fluxMapTestFilter() {
		
		var fluxFilter = c.printFluxFilter();
		
		StepVerifier.create(fluxFilter)
			.expectNext("IRON", "ASIA")
			.verifyComplete();
	}
	
	@Test
	void printMonoFlatMap() {
		var mono = c.printMonoFlatMap();
		StepVerifier.create(mono)
		.expectNextCount(1)
		.verifyComplete();
	}
	
	@Test
	void monoTest() {		
		Mono<String> mono = c.printMono();
		
		StepVerifier.create(mono)
			.expectNext("Detonator")
			.verifyComplete();		
	}

	@Test
	void printMonoFlatMapMany() {
		var flux = c.printMonoFlatMapMany();
		StepVerifier.create(flux)
				.expectNextCount(9)
				.verifyComplete();
	}

	@Test
	void printFluxTransform() {
		var flux = c.printFluxTransform(4);
		StepVerifier.create(flux)
				.expectNextCount(2)
				.verifyComplete();
	}

	@Test
	void printFluxTransformDefaultIfEmpty() {
		var flux = c.printFluxTransformDefaultIfEmpty(1000);
		StepVerifier.create(flux)
				.expectNext("Default")
				.verifyComplete();
	}

	@Test
	void printFluxSwitchIfEmpty() {
		var flux = c.printFluxSwitchIfEmpty(9);
		StepVerifier.create(flux)
				.expectNext("Led Zeppelin", "Pink Floyd")
				.verifyComplete();
	}

	@Test
	void printFluxConcat() {
		var flux = c.printFluxConcat();

		StepVerifier.create(flux)
				.expectNext("Iron", "Metallica", "Megadeth", "Asia")
				.verifyComplete();
	}

	@Test
	void printFluxConcatWith() {
		var flux = c.printFluxConcatWith();

		StepVerifier.create(flux)
				.expectNext("Iron", "Metallica", "Megadeth", "Asia")
				.verifyComplete();
	}

    @Test
    void printFluxMerge() {
		var flux = c.printFluxMerge();

		StepVerifier.create(flux)
				.expectNext("Iron", "Megadeth", "Metallica", "Asia")
				.verifyComplete();
    }

	@Test
	void printFluxMergeWith() {
		var flux = c.printFluxMergeWith();

		StepVerifier.create(flux)
				.expectNext("Iron", "Megadeth", "Metallica", "Asia")
				.verifyComplete();
	}

	@Test
	void printFluxMergeSequential() {
		var flux = c.printFluxMergeSequential();

		StepVerifier.create(flux)
				.expectNext("Iron", "Metallica", "Megadeth", "Asia")
				.verifyComplete();
	}

	@Test
	void printFluxZipTest() {
		var flux = c.printFluxZip();

		StepVerifier.create(flux)
				.expectNext("IronMegadeth", "MetallicaAsia")
				.verifyComplete();
	}

	@Test
	void printFluxZipWithTest() {
		var flux = c.printFluxZipWith();

		StepVerifier.create(flux)
				.expectNext("IronMegadeth", "MetallicaAsia")
				.verifyComplete();
	}

	@Test
	void  printFluxTuple(){
		Flux<String> flux1 = Flux.just("Iron", "Metallica");
		Flux<String> flux2 = Flux.just("Megadeth", "Asia");
		Flux<String> flux3 = Flux.just("Dream Theater", "Symphony X");

		var flux = c.printFluxTuple();

		StepVerifier.create(flux)
				.expectNext("IronMegadethDream Theater", "MetallicaAsiaSymphony X")
				.verifyComplete();
	}

	@Test
	void printDoOnNext() {
		var flux1 = c.printDoOnNext();
		StepVerifier.create(flux1)
				.expectNext("Iron", "Metallica", "Megadeth", "Asia")
				.verifyComplete();
	}

	@Test
	void printDoOnError() {
		var flux1 = c.printDoOnError();
		StepVerifier.create(flux1)
				.expectNext("Iron", "Metallica", "Megadeth", "Asia", "Epica")
				.verifyComplete();
	}

	@Test
	void printDoOnErrorContinue() {
		var flux1 = c.printDoOnErrorContinue();
		StepVerifier.create(flux1)
				.expectNext("IRON", "MEGADETH", "ASIA")
				.verifyComplete();
	}

	@Test
	void printDoOnErrorMap() {
		var flux1 = c.printDoOnErrorMap();
		StepVerifier.create(flux1)
				.expectNext("IRON")
				.expectError(IllegalStateException.class)
				.verify();
	}

	@Test
	void printOnError() {
		var flux1 = c.printOnError();
		StepVerifier.create(flux1)
				.expectNext("IRON")
				.expectError(RuntimeException.class)
				.verify();
	}
}