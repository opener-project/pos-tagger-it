[![Build Status](https://drone.io/github.com/opener-project/pos-tagger-it/status.png)](https://drone.io/github.com/opener-project/pos-tagger-it/latest)

# Italian POS Tagger

This repository contains the source code for the Italian POS tagger of the
OpeNER project.

## Requirements

* Java 1.7 or newer
* Ruby 1.9.2 or newer
* Maven
* Bundler

## Installation

Using RubyGems:

    gem install opener-pos-tagger-it

Using Bundler:

    gem 'opener-pos-tagger-it',
      :git    => 'git@github.com/opener-project/pos-tagger-it.git',
      :branch => 'master'

Using specific install:

    gem install specific_install
    gem specific_install opener-pos-tagger-it \
        -l https://github.com/opener-project/pos-tagger-it.git

## Usage

    cat some_input_file.kaf | pos-tagger-it

## Contributing

First make sure all the required dependencies are installed:

    bundle install

Then compile the required Java code:

    bundle exec rake java:compile

For this you'll need to have Java 1.7 and Maven installed. These requirements
are verified for you before the Rake task calls Maven.

## Testing

To run the tests (which are powered by Cucumber), simply run the following:

    bundle exec rake

This will take care of verifying the requirements, installing the required Java
packages and running the tests.

For more information on the available Rake tasks run the following:

    bundle exec rake -T

## Structure

This repository comes in two parts: a collection of Java source files and Ruby
source files. The Java code can be found in the `core/` directory, everything
else will be Ruby source code.
