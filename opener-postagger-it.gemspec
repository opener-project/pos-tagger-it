require File.expand_path('../lib/opener/postagger/it/version', __FILE__)

GENERATED_FILES = Dir.glob(
  File.expand_path("../core/target/synthema-pos-tagger_it-*.jar", __FILE__)
)

Gem::Specification.new do |gem|
  gem.name                  = 'opener-postagger-it'
  gem.version               = Opener::Postagger::It::VERSION
  gem.authors               = ['development@olery.com']
  gem.summary               = 'Italian postagger: Takes KAF with text layer and returns KAF with term layer added'
  gem.description           = gem.summary
  gem.homepage              = 'http://opener-project.github.com/'
  gem.has_rdoc              = "yard"
  gem.required_ruby_version = ">= 1.9.2"

  gem.files       = `git ls-files`.split("\n").push('core/target/synthema-pos-tagger_it-1.0.jar') #+ GENERATED_FILES
  gem.executables = gem.files.grep(%r{^bin/}).map{ |f| File.basename(f) }
  gem.test_files  = gem.files.grep(%r{^(test|spec|features)/})

  gem.add_development_dependency 'cucumber'
  gem.add_development_dependency 'rspec'
end
