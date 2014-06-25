require File.expand_path('../lib/opener/pos_taggers/it/version', __FILE__)

Gem::Specification.new do |gem|
  gem.name          = "opener-pos-tagger-it"
  gem.version       = Opener::POSTaggers::IT::VERSION
  gem.authors       = ["development@olery.com"]
  gem.summary       = "POS tagging for italian"
  gem.description   = gem.summary
  gem.homepage      = "http://opener-project.github.com/"
  gem.has_rdoc      = "yard"
  gem.required_ruby_version = ">= 1.9.2"

  gem.license = 'Apache 2.0'

  gem.files = Dir.glob([
    'core/target/synthema-pos-tagger_it-*.jar',
    'lib/**/*',
    '*.gemspec',
    'README.md',
    'LICENSE.txt'
  ]).select { |file| File.file?(file) }

  gem.executables = Dir.glob('bin/*').map { |file| File.basename(file) }

  gem.add_development_dependency 'rspec', '~> 3.0'
  gem.add_development_dependency 'cucumber'
  gem.add_development_dependency 'rake'
  gem.add_development_dependency 'cliver'
end
