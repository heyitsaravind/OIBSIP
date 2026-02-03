# Contributing to RailConnect

Thank you for your interest in contributing to RailConnect! This document provides guidelines for contributing to this train booking management system.

## 🚀 Getting Started

1. Fork the repository
2. Clone your fork locally
3. Create a new branch for your feature/fix
4. Make your changes
5. Test thoroughly
6. Submit a pull request

## 📋 Development Setup

### Prerequisites
- Java 11 or higher
- MySQL 8.0+
- Maven 3.6+

### Setup Steps
1. Clone the repository
2. Set up MySQL database using `database_schema.sql`
3. Update database credentials in `DatabaseConnectionManager.java`
4. Run `mvn clean compile` to build
5. Run `mvn exec:java -Dexec.mainClass="com.reservation.TrainBookingApplication"`

## 🎯 Areas for Contribution

### High Priority
- [ ] Unit tests for service classes
- [ ] Integration tests for database operations
- [ ] Input validation improvements
- [ ] Error handling enhancements

### Medium Priority
- [ ] GUI implementation using JavaFX
- [ ] REST API development
- [ ] Email notification system
- [ ] Payment gateway integration

### Low Priority
- [ ] Performance optimizations
- [ ] Additional travel classes
- [ ] Reporting features
- [ ] Admin dashboard

## 📝 Code Style Guidelines

### Java Conventions
- Use camelCase for variables and methods
- Use PascalCase for class names
- Use UPPER_SNAKE_CASE for constants
- Add JavaDoc comments for public methods
- Keep methods under 50 lines when possible

### Database Conventions
- Use snake_case for table and column names
- Add appropriate indexes for performance
- Use foreign key constraints
- Include audit fields (created_at, updated_at)

### Git Conventions
- Use descriptive commit messages
- Prefix commits with type: `feat:`, `fix:`, `docs:`, `refactor:`
- Keep commits atomic and focused
- Reference issues in commit messages

## 🧪 Testing Guidelines

### Unit Tests
- Test all service layer methods
- Mock database dependencies
- Test edge cases and error conditions
- Aim for 80%+ code coverage

### Integration Tests
- Test database operations
- Test complete user workflows
- Test error scenarios

## 📚 Documentation

### Code Documentation
- Add JavaDoc for all public classes and methods
- Include parameter descriptions and return values
- Document exceptions that may be thrown
- Add usage examples for complex methods

### User Documentation
- Update README.md for new features
- Add setup instructions for new dependencies
- Include troubleshooting guides
- Provide usage examples

## 🐛 Bug Reports

When reporting bugs, please include:
- Java version and OS
- MySQL version
- Steps to reproduce
- Expected vs actual behavior
- Error messages and stack traces
- Screenshots if applicable

## 💡 Feature Requests

For new features, please provide:
- Clear description of the feature
- Use cases and benefits
- Proposed implementation approach
- Any breaking changes
- Documentation requirements

## 🔍 Code Review Process

### Before Submitting
- [ ] Code compiles without warnings
- [ ] All tests pass
- [ ] Code follows style guidelines
- [ ] Documentation is updated
- [ ] No sensitive data in commits

### Review Criteria
- Code quality and readability
- Performance implications
- Security considerations
- Test coverage
- Documentation completeness

## 📞 Getting Help

- Create an issue for bugs or feature requests
- Use discussions for questions
- Check existing issues before creating new ones
- Provide detailed information in your requests

## 🏆 Recognition

Contributors will be recognized in:
- README.md contributors section
- Release notes for significant contributions
- Special mentions for innovative features

## 📄 License

By contributing, you agree that your contributions will be licensed under the same license as the project.

---

Thank you for helping make RailConnect better! 🚂✨