package com.dailybuffer.reactiveprogrammingtutorial.services;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.management.RuntimeErrorException;

// https://www.youtube.com/watch?v=O26jhgk682Q&t=5277s&ab_channel=DailyCodeBuffer
public class FluxAndMonoServices {

	public static void main(String[] args) {
		FluxAndMonoServices f = new FluxAndMonoServices();
		f.printFlux().subscribe();
		f.printMono().subscribe();
		f.printFluxMap().subscribe();
	}
	
	public Flux<String> printFlux() {		
		return Flux.fromIterable(List.of("Iron", "Metallica", "Megadeth", "Asia"));
	}
	
	public Flux<String> printFluxMap() {
		return Flux.fromIterable(List.of("Iron", "Metallica", "Megadeth", "Asia"))
				.map(String::toUpperCase);
	}

	public Flux<String> printFlatMap() {
		return Flux.fromIterable(List.of("Iron", "Metallica", "Megadeth", "Asia"))
				.flatMap(s -> Flux.just(s.split("")));
	}
	
	public Flux<String> printFlatMapAsync() {
		return Flux.fromIterable(List.of("Iron", "Metallica", "Megadeth", "Asia"))
				.flatMap(s -> Flux.just(s.split(""))
						.delayElements(Duration.ofMillis(
								new Random().nextInt(100)
						))).log();
	}

	public Flux<String> printFlatConcatMap() {
		return Flux.fromIterable(List.of("Iron", "Metallica", "Megadeth", "Asia"))
				.concatMap(s -> Flux.just(s.split(""))
						.delayElements(Duration.ofMillis(
								new Random().nextInt(100)
						))).log();
	}
	
	public Flux<String> printFluxFilter(){
		return Flux.fromIterable(List.of("Iron", "Metallica", "Megadeth", "Asia"))
				.filter(s -> s.length() == 4)
				.map(String::toUpperCase);				
	}

	public Mono<List<String>> printMonoFlatMap() {
		return Mono.just("Detonator")
				.flatMap(s -> Mono.just(List.of(s.split(""))));
	}

	public Flux<String> printMonoFlatMapMany() {
		return Mono.just("Detonator")
				.flatMapMany(s -> Flux.just(s.split("")));
	}

	public Flux<String> printFluxTransform(final int number){

		Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > 4);
		return Flux.fromIterable(List.of("Iron", "Metallica", "Megadeth", "Asia"))
				.transform(filterData);
	}

	public Flux<String> printFluxTransformDefaultIfEmpty(final int number){

		Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > number);
		return Flux.fromIterable(List.of("Iron", "Metallica", "Megadeth", "Asia"))
				.transform(filterData)
				.defaultIfEmpty("Default");
	}

	public Flux<String> printFluxSwitchIfEmpty(final int number){

		Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > number);
		return Flux.fromIterable(List.of("Iron", "Metallica", "Megadeth", "Asia"))
				.transform(filterData)
				.switchIfEmpty(Flux.just("Led Zeppelin", "Pink Floyd")
							.transform(filterData));
	}

	public Flux<String> printFluxConcat(){
		Flux<String> flux1 = Flux.just("Iron", "Metallica");
		Flux<String> flux2 = Flux.just("Megadeth", "Asia");

		return Flux.concat(flux1, flux2);
	}

	public Flux<String> printFluxConcatWith(){
		Flux<String> flux1 = Flux.just("Iron", "Metallica");
		Flux<String> flux2 = Flux.just("Megadeth", "Asia");

		return flux1.concatWith(flux2);
	}

	public Flux<String> printFluxMerge(){
		Flux<String> flux1 = Flux.just("Iron", "Metallica")
						.delayElements(Duration.ofMillis(50));
		Flux<String> flux2 = Flux.just("Megadeth", "Asia")
						.delayElements(Duration.ofMillis(75));

		return Flux.merge(flux1, flux2);
	}

	public Flux<String> printFluxMergeWith(){
		Flux<String> flux1 = Flux.just("Iron", "Metallica")
				.delayElements(Duration.ofMillis(50));
		Flux<String> flux2 = Flux.just("Megadeth", "Asia")
				.delayElements(Duration.ofMillis(75));

		return flux1.mergeWith(flux2);
	}

	public Flux<String> printFluxMergeSequential(){
		Flux<String> flux1 = Flux.just("Iron", "Metallica")
				.delayElements(Duration.ofMillis(50));
		Flux<String> flux2 = Flux.just("Megadeth", "Asia")
				.delayElements(Duration.ofMillis(75));

		return Flux.mergeSequential(flux1, flux2);
	}

	public Flux<String> printFluxZip(){
		Flux<String> flux1 = Flux.just("Iron", "Metallica");
		Flux<String> flux2 = Flux.just("Megadeth", "Asia");

		return Flux.zip(flux1, flux2, (f1, f2) -> f1 + f2);
	}

	public Flux<String> printFluxZipWith(){
		Flux<String> flux1 = Flux.just("Iron", "Metallica");
		Flux<String> flux2 = Flux.just("Megadeth", "Asia");

		return flux1.zipWith(flux2,  (f1, f2) -> f1 + f2);
	}

	public Flux<String> printFluxTuple(){
		Flux<String> flux1 = Flux.just("Iron", "Metallica");
		Flux<String> flux2 = Flux.just("Megadeth", "Asia");
		Flux<String> flux3 = Flux.just("Dream Theater", "Symphony X");

		return flux1.zip(flux1, flux2, flux3)
				.map(objects -> objects.getT1() + objects.getT2() + objects.getT3());
	}

	public Flux<String> printDoOnNext(){
		Flux<String> flux1 = Flux.fromIterable(List.of("Iron", "Metallica", "Megadeth", "Asia"))
				.doOnNext(s -> {
					System.out.println("----> = " + s);
				})
				.doOnSubscribe(s -> {
					System.out.println("subscription.toString() = " + s.toString());
				})
				.doOnComplete(() -> {
					System.out.println("Completed");
				});
		return flux1;
	}

	public Flux<String> printDoOnError(){

		Flux<String> flux = Flux.just("Iron", "Metallica", "Megadeth", "Asia")
				.concatWith(Flux.error(
						new RuntimeException("Exception HERE")
				))
				.onErrorReturn("Epica");
		return flux;
	}

	public Flux<String> printDoOnErrorContinue(){
		return Flux.just("Iron", "Metallica", "Megadeth", "Asia")
				.map(s -> {
					if(s.equalsIgnoreCase("Metallica")){
						throw new RuntimeException("Exception");
					} else {
						return s.toUpperCase();
					}
				})
				.onErrorContinue( (ex, obj) -> {
					System.out.println("ex = " + ex);
					System.out.println("obj = " + obj);
				});
	}

	public Flux<String> printDoOnErrorMap(){
		return Flux.just("Iron", "Metallica", "Megadeth", "Asia")
				.map(s -> {
					if(s.equalsIgnoreCase("Metallica")){
						throw new RuntimeException("Exception");
					} else {
						return s.toUpperCase();
					}
				})
				.onErrorMap( throwable -> {
					System.out.println("throwable = " + throwable);
					return  new IllegalStateException("From onErrorMap");
				});
	}

	public Flux<String> printOnError(){
		return Flux.just("Iron", "Metallica", "Megadeth", "Asia")
				.map(s -> {
					if(s.equalsIgnoreCase("Metallica"))
						throw new RuntimeException("Exception");
					return s.toUpperCase();
				})
				.doOnError( throwable -> {
					System.out.println("throwable = " + throwable);
				});
	}

	public Mono<String> printMono() {
		return Mono.just("Detonator");
	}
}