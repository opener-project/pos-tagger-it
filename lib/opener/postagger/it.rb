require_relative 'it/version'

module Opener
  module Postagger
    class It
      attr_reader :language

      def initialize(opts={})
        @language = opts[:language]
      end

      def command(opts=[])
        "java -jar #{kernel} #{opts.join(' ')}"
      end

      def run(opts=ARGV)
        `#{command(opts)}`
      end

      protected

      def core_dir
        File.expand_path("../../../../core/target", __FILE__)
      end

      def kernel
        File.join(core_dir,'synthema-pos-tagger_it-1.0.jar')
      end

      def lib
        File.join(core_dir,'lib/') # Trailing / is required
      end

      def language
        return @language.nil? ? nil : "-l #{@language}"
      end

    end

  end
end
