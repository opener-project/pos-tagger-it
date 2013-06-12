# Opener::POSTaggers::IT 

This module uses Apache OpenNLP programatically to perform POS tagging.
It has been developed by the IXA NLP Group (ixa.si.ehu.es).

+ Italian Perceptron models have been trained using a SyNTHEMA corpus.

+ Code refactoring done by Vicomtech (maven, some refactoring to java packages/classes, and ruby wrapping following the new naming convention)

## Installation

Now the inner Java core uses Maven to build itself.
Provided you have Apache Maven 3 already installed, clone the repository, enter the just created directory, and simply issue:

	$ sudo rake install

It should be all.

## Usage

Once installed as a gem you can access it from anywhere:

The command reads a KAF file with text layer from standard input:

````shell
cat SOME_KAF_TOKENIZED_FILE | pos-tagger-it
````
And outputs the same KAF with the term layer added to the standard output.
The command can also receive a -t argument to return a fixed timestamp (for test purposes)


## Contributing

1. Pull it
2. Create your feature branch (`git checkout -b features/my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin features/my-new-feature`)
5. If you're confident, merge your changes into master.


Contact information
===================

````shell
Francesco Rubino
Synthema srl
Pisa (Italy)
francesco.rubino@synthema.it
````
