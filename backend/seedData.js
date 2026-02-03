const mongoose = require('mongoose');
const AssessmentQuestion = require('./models/Question');
const Student = require('./models/User');
require('dotenv').config();

const sampleAssessmentQuestions = [
  {
    questionText: "Which planet is known as the 'Red Planet' due to its distinctive color?",
    answerChoices: [
      { optionText: "Venus", isCorrectAnswer: false },
      { optionText: "Mars", isCorrectAnswer: true },
      { optionText: "Jupiter", isCorrectAnswer: false },
      { optionText: "Saturn", isCorrectAnswer: false }
    ],
    subject: "Astronomy",
    difficultyLevel: "beginner"
  },
  {
    questionText: "In web development, which language is primarily used for client-side scripting?",
    answerChoices: [
      { optionText: "Python", isCorrectAnswer: false },
      { optionText: "JavaScript", isCorrectAnswer: true },
      { optionText: "PHP", isCorrectAnswer: false },
      { optionText: "Ruby", isCorrectAnswer: false }
    ],
    subject: "Computer Science",
    difficultyLevel: "beginner"
  },
  {
    questionText: "What does the acronym 'HTTP' stand for in web technology?",
    answerChoices: [
      { optionText: "HyperText Transfer Protocol", isCorrectAnswer: true },
      { optionText: "High-Tech Transfer Process", isCorrectAnswer: false },
      { optionText: "Home Terminal Transfer Protocol", isCorrectAnswer: false },
      { optionText: "Hyperlink Text Transfer Process", isCorrectAnswer: false }
    ],
    subject: "Web Technology",
    difficultyLevel: "beginner"
  },
  {
    questionText: "Which data structure follows the Last-In-First-Out (LIFO) principle?",
    answerChoices: [
      { optionText: "Queue", isCorrectAnswer: false },
      { optionText: "Array", isCorrectAnswer: false },
      { optionText: "Stack", isCorrectAnswer: true },
      { optionText: "Linked List", isCorrectAnswer: false }
    ],
    subject: "Data Structures",
    difficultyLevel: "intermediate"
  },
  {
    questionText: "What is the largest ocean on Earth by surface area?",
    answerChoices: [
      { optionText: "Atlantic Ocean", isCorrectAnswer: false },
      { optionText: "Pacific Ocean", isCorrectAnswer: true },
      { optionText: "Indian Ocean", isCorrectAnswer: false },
      { optionText: "Arctic Ocean", isCorrectAnswer: false }
    ],
    subject: "Geography",
    difficultyLevel: "beginner"
  },
  {
    questionText: "In React.js, what is the purpose of the 'useState' hook?",
    answerChoices: [
      { optionText: "To manage component state", isCorrectAnswer: true },
      { optionText: "To handle HTTP requests", isCorrectAnswer: false },
      { optionText: "To create routing", isCorrectAnswer: false },
      { optionText: "To style components", isCorrectAnswer: false }
    ],
    subject: "React Development",
    difficultyLevel: "intermediate"
  },
  {
    questionText: "Which chemical element has the symbol 'Au' on the periodic table?",
    answerChoices: [
      { optionText: "Silver", isCorrectAnswer: false },
      { optionText: "Gold", isCorrectAnswer: true },
      { optionText: "Aluminum", isCorrectAnswer: false },
      { optionText: "Argon", isCorrectAnswer: false }
    ],
    subject: "Chemistry",
    difficultyLevel: "beginner"
  },
  {
    questionText: "What is the time complexity of binary search algorithm?",
    answerChoices: [
      { optionText: "O(n)", isCorrectAnswer: false },
      { optionText: "O(log n)", isCorrectAnswer: true },
      { optionText: "O(n²)", isCorrectAnswer: false },
      { optionText: "O(1)", isCorrectAnswer: false }
    ],
    subject: "Algorithms",
    difficultyLevel: "intermediate"
  }
];

const initializeDatabase = async () => {
  try {
    await mongoose.connect(process.env.MONGODB_URI || 'mongodb://localhost:27017/eduexam_pro_db');
    console.log('✅ Connected to MongoDB successfully');

    // Clear existing data
    await AssessmentQuestion.deleteMany({});
    await Student.deleteMany({});
    console.log('🧹 Cleared existing database records');

    // Insert sample questions
    await AssessmentQuestion.insertMany(sampleAssessmentQuestions);
    console.log('📚 Sample assessment questions added');

    // Create demo student account
    const demoStudent = new Student({
      fullName: 'Demo Student',
      emailAddress: 'student@eduexam.com',
      loginPassword: 'demo2024',
      contactNumber: '+1-555-0123'
    });
    await demoStudent.save();
    console.log('👤 Demo student account created');

    console.log('\n🎉 Database initialization completed successfully!');
    console.log('\n📋 Demo Login Credentials:');
    console.log('   Email: student@eduexam.com');
    console.log('   Password: demo2024');
    
    process.exit(0);
  } catch (error) {
    console.error('❌ Database initialization failed:', error);
    process.exit(1);
  }
};

initializeDatabase();