# Opener::Kernel::SYNTHEMA::POSTagger::IT

This module uses Apache OpenNLP programatically to perform POS tagging.
It has been developed by the IXA NLP Group (ixa.si.ehu.es).

+ Italian Perceptron models have been trained using a SyNTHEMA corpus.

## Installation

Add this line to your application's Gemfile:

    gem 'Synthema-pos-tagger_IT_kernel', :git=>"git@github.com/opener-project/Synthema-pos-tagger_IT_kernel.git"

And then execute:

    $ bundle install

Or install it yourself as:

    $ gem specific_install Synthema-pos-tagger_IT_kernel -l https://github.com/opener-project/Synthema-pos-tagger_IT_kernel.git


If you dont have specific_install already:

    $ gem install specific_install

## Usage

Once installed as a gem you can access the gem from anywhere:


TODO: Change output below as needed
````shell
echo "foo" | Synthema-pos-tagger_IT_kernel
````

Will output

````
oof
````

## Contributing

1. Pull it
2. Create your feature branch (`git checkout -b features/my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin features/my-new-feature`)
5. If you're confident, merge your changes into master.



Contents
========

The contents of the module are the following:

    + syn_postagger_lite_IT.jar     Synthema pos-tagger core


INSTALLATION
============

Installing the synthema-postagger requires the following steps:

If you already have installed in your machine JDK6, please go to step 2
directly. Otherwise, follow these steps:

1. Install JDK 1.6
-------------------

If you do not install JDK 1.6 in a default location, you will probably need to configure the PATH in .bashrc or .bash_profile:

````shell
export JAVA_HOME=/yourpath/local/java6
export PATH=${JAVA_HOME}/bin:${PATH}
````

If you use tcsh you will need to specify it in your .login as follows:

````shell
setenv JAVA_HOME /usr/java/java16
setenv PATH ${JAVA_HOME}/bin:${PATH}
````

If you re-login into your shell and run the command

````shell
java -version
````

You should now see that your jdk is 1.6


2. Get module source code
--------------------------

````shell
git clone git@github.com:opener-project/Synthema-pos-tagger_IT_kernel.git
````

3. Download models and other resources
--------------------------------------

To perform Italian lemmatization the module uses a plain text dictionary: lemma_pos.dict.


4. Move into main directory
---------------------------

````shell
cd $REPO/core
````

5. USING Synthema-postagger
================

The program accepts tokenized text in KAF format as standard input and outputs KAF.

You can get the tokenized input for this module from Vicom-tokenizer module. To run the program execute:

````shell
java -jar $PATH/target/syn_postagger_lite_IT.jar -op tagging -k $kaf -m $model -d $lemma_pos.dict
````

See

````shell
java -jar $PATH/target/ehu-pos-1.0.jar
````

for more options running the module



Contact information
===================

````shell
Francesco Rubino
Synthema srl
Pisa (Italy)
francesco.rubino@synthema.it
````
