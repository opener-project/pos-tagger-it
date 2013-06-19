require File.expand_path('../lib/opener/pos_taggers/it/version', __FILE__)

generated = Dir.glob('core/target/synthema-pos-tagger_it-*.jar')

Gem::Specification.new do |gem|
  gem.name          = "opener-pos-tagger-it"
  gem.version       = Opener::POSTaggers::IT::VERSION
  gem.authors       = ["development@olery.com"]
  gem.summary       = "POS tagging for italian"
  gem.description   = gem.summary
  gem.homepage      = "http://opener-project.github.com/"
  gem.has_rdoc      = "yard"
  gem.required_ruby_version = ">= 1.9.2"

  gem.files         = (`git ls-files`.split("\n") + generated).sort
  gem.executables   = gem.files.grep(%r{^bin/}).map{ |f| File.basename(f) }
  gem.test_files    = gem.files.grep(%r{^(test|spec|features)/})

  gem.add_dependency 'opener-build-tools'

  gem.add_development_dependency 'rspec'
  gem.add_development_dependency 'cucumber'
  gem.add_development_dependency 'rake'
end
