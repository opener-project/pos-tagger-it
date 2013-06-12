require_relative 'it/version'

module Opener
  module POSTaggers
    class IT
      attr_reader :kernel, :lib

      def command(opts=[])
        "java -jar #{kernel} -l it #{opts.join(' ')}"
      end
      
      def run(opts=ARGV)
        `#{command(opts)}`
      end
      
      protected
      
      def core_dir
        File.expand_path("../../../core/target", File.dirname(__FILE__))
      end
      
      def kernel
        core_dir+'/synthema-pos-tagger_it-1.0.jar'
      end      
      
    end
  end
end







