require_relative '../../lib/opener/pos_taggers/it'
require 'tempfile'

def kernel_root
  File.expand_path("../../../", __FILE__)
end

def kernel
  Opener::POSTaggers::IT.new
end