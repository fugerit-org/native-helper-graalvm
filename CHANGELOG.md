# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Generation based on all class in a package (packageName parameter)

## [1.3.2] - 2024-04-01

### Added

- NativeHelperFacade.generate(NativeHelperConfig,File) method

## [1.3.1] - 2024-04-01

### Changed

- Loading classes using ClassHelper.getDefaultClassLoader().loadClass() 
instead of Class.forName().

## [1.3.0] - 2024-04-01

### Added

- reflect-config.json generation capability
- NativeHelperFacade support for typeReachable condition

## [1.2.0] - 2024-03-31

### Added

- NativeHelperFacade utilities

### Changed

- Improved README documentation

## [1.1.1] - 2024-03-24

### Added

- fixedSort method in EntryHelper

## [1.1.0] - 2024-03-24

### Added

- EntryHelper for constructors creations
- GenerateReflectConfig API to generate reflect-config entry

### Changed

- fj-bom set to 1.6.3
- fj-core set to 8.5.3

## [1.0.0] - 2024-03-01

### Added

- GenerateReflectConfig API