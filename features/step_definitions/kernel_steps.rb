Given /^input file's language "(.*?)"$/ do |language|
  @kernel = Opener::POSTaggers::IT.new(:test=>true)
end

Given /^the file name "(.*?)"$/ do |name|
  @name = name
end

Given /^the fixture file "(.*?)"$/ do |filename|
  @input = fixture_file(filename)
end

Given /^I put them through the kernel$/ do
  @output = @kernel.run(File.open(@input, "r"))
end

Then /^the output should match the fixture "(.*?)"$/ do |filename|
  fixture_output = File.read(fixture_file(filename))
  @output.should eql(fixture_output)
end

def fixture_file(filename)
  File.absolute_path("features/fixtures/", kernel_root) + "/#{filename}"
end
