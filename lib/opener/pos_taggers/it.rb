require 'open3'
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
      # Builds the command used to execute the kernel.
      #
      # @return [String]
      #
      def command
        "java -jar #{kernel} -l #{lang} #{args.join(' ')}"
      end

      ##
      # Runs the command and returns the output of STDOUT, STDERR and the
      # process information.
      #
      # @param [String] input The input to tag.
      # @return [Array]
      #
      def run(input)
        return Open3.capture3(command, :stdin_data => input)
      end

      protected

      ##
      # @return [String]
      #
      def core_dir
        File.expand_path("../../../core", File.dirname(__FILE__))
      end  

      ##
      # @return [String]
      #
      def kernel
        core_dir+'/target/synthema-pos-tagger_it-1.0.jar'
      end 
      
      def lang
        'it'
      end
      
    end # IT
  end # POSTaggers
end # Opener



















