require 'open3'
require 'stringio'
require 'java'

require File.expand_path('../../../../core/target/synthema-pos-tagger_it-1.1.jar', __FILE__)

import 'eu.openerproject.kaf.layers.KafMetadata'
import 'eu.openerproject.kaf.layers.KafTarget'
import 'eu.openerproject.kaf.layers.KafTerm'
import 'eu.openerproject.kaf.layers.KafWordForm'
import 'eu.openerproject.kaf.reader.KafSaxParser'

require_relative 'it/version'

module Opener
  module POSTaggers
    ##
    # The POS tagger that supports Italian.
    #
    # @!attribute [r] args
    #  @return [Array]
    # @!attribute [r] options
    #  @return [Hash]
    #
    class IT
      attr_reader :args, :options

      ##
      # @param [Hash] options
      #
      # @option options [Array] :args The commandline arguments to pass to the
      #  underlying Python script.
      #
      def initialize(options = {})
        @args          = options.delete(:args) || []
        @options       = options
      end

      ##
      # Runs the command and returns the output of STDOUT, STDERR and the
      # process information.
      #
      # @param [String] input The input to tag.
      # @return [Array]
      #
      def run(input)
        input  = StringIO.new(input) unless input.kind_of?(IO)
        cli    = Java::it.synthema.opener.postagger.it.CLI.new
        result = cli.process_to_string(input.to_inputstream, test_mode?)

        return result
      end

      protected

      def test_mode?
        return options.fetch(:test, false) || args.include?("-t")
      end

      def lang
        'it'
      end

    end # IT
  end # POSTaggers
end # Opener

