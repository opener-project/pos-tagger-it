require_relative '../../lib/opener/pos_taggers/it'
require 'tempfile'
require 'rspec'

def kernel_root
  File.expand_path("../../../", __FILE__)
end

def kernel
  Opener::POSTaggers::IT.new
end

RSpec.configure do |config|
  config.expect_with :rspec do |c|
    c.syntax = [:should, :expect]
  end

  config.mock_with :rspec do |c|
    c.syntax = [:should, :expect]
  end
end
