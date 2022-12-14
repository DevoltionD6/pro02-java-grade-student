import java.util.Scanner;

public class asm2 {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        begin();
        double[] midTermResult = midTerm();
        double[] finalTermResult = finalTerm((int) midTermResult[1]);
        double[] homeworkResult = homework((int) midTermResult[1], (int) finalTermResult[1]);
        report(midTermResult[0], finalTermResult[0], homeworkResult[0]);
    }

    // thông báo
    public static void begin() {
        System.out.println("This program reads exam/homework scores and " +
                "reports your overall course grade.");
        System.out.println();
    }

    public static double[] midTerm() {
        System.out.println("Midterm:");
        int weight;
        while (true) {
            try {
                System.out.print("Weight (0 - 100)? ");
                weight = input.nextInt();
                if ((weight < 100) && (weight > 0)) {
                    break;
                }
            } catch (Exception e) {
                input.next();
                continue;
            }
        }
        return scoreProgress(weight);
    }

    public static double[] finalTerm(int weightMidTerm) {
        System.out.println("Final:");
        int weight;
        while (true) {
            try {
                System.out.print("Weight (0 - 100)? ");
                weight = input.nextInt();
                if ((weight + weightMidTerm < 100) && (weight > 0)) {
                    break;
                }
            } catch (Exception e) {
                input.next();
                continue;
            }
        }
        return scoreProgress(weight);
    }

    // bắt lỗi đầu vào
    public static double catchErrorInputDouble(String mess) {
        double value;
        while (true) {
            try {
                System.out.print(mess);
                value = input.nextDouble();
                if (value > 0) {
                    return value;
                }
            } catch (Exception e) {
                input.next();
                continue;
            }
        }
    }

    public static int catchErrorInputInt(String mess) {
        int value;
        while (true) {
            try {
                System.out.print(mess);
                value = input.nextInt();
                if (value > 0) {
                    return value;
                }
            } catch (Exception e) {
                input.next();
                continue;
            }
        }
    }

    public static double[] scoreProgress(int weight) {
        double scoreEarned = catchErrorInputDouble("Score earned? ");
        int isScoreShifted;
        while (true) {
            try {
                System.out.print("Were scores shifted (1=yes, 2=no)? ");
                isScoreShifted = input.nextInt();
                if (isScoreShifted == 1 || isScoreShifted == 2) {
                    break;
                }
            } catch (Exception e) {
                input.next();
                continue;
            }
        }
        double shiftAmount = 0;
        if (isScoreShifted == 1) {
            shiftAmount = catchErrorInputDouble("Shift amount? ");
        }
        double totalPoints = (scoreEarned + shiftAmount > 100) ? 100 : scoreEarned + shiftAmount;
        double weightedScore = ((double) totalPoints / 100) * weight;
        int finalPoint = (int) Math.round(totalPoints);
        System.out.println("Total points = " + finalPoint + " / 100");
        System.out.println("Weighted score = " + String.format("%.1f", weightedScore) + " / " + weight);
        System.out.println();
        return new double[] { weightedScore, weight };
    }

    public static double[] homework(int weightMidTerm, int weightFinalTerm) {
        System.out.println("Homework:");
        int weight;
        while (true) {
            try {
                System.out.print("Weight (0 - 100)? ");
                weight = input.nextInt();
                if ((weightFinalTerm + weightMidTerm + weight == 100) && (weight > 0)) {
                    break;
                }
            } catch (Exception e) {
                input.next();
                continue;
            }
        }
        int assignments = catchErrorInputInt("Number of assignments? ");
        double asmScores = 0;
        int asmMaxScores = 0;
        for (int i = 1; i <= assignments; i++) {
            while (true) {
                try {
                    System.out.print("Assignment " + i + " score and max? ");
                    double score = input.nextDouble();
                    int maxScore = input.nextInt();
                    if (score > 0 && maxScore > 0) {
                        asmScores += score;
                        asmMaxScores += maxScore;
                        break;
                    }
                } catch (Exception e) {
                    input.nextLine();
                    continue;
                }
            }
        }
        int totalMaxScores = (asmMaxScores > 150) ? 150 : asmMaxScores;
        double totalScores = (asmScores > totalMaxScores) ? totalMaxScores : asmScores;
        int sections = catchErrorInputInt("How many section did you attend? ");
        int sectionPoints = (sections * 5 > 30) ? 30 : sections * 5;
        System.out.println("Section points = " + sectionPoints + " / 30");
        totalMaxScores += 30;
        totalScores += sectionPoints;
        double weightedScore = ((double) totalScores / totalMaxScores) * weight;
        int totalPoints = (int) Math.round(totalScores);
        System.out.println("Total point = " + totalPoints + " / " + totalMaxScores);
        System.out.println("Weighted score = " + String.format("%.1f", weightedScore) + " / " + weight);
        System.out.println();
        return new double[] { weightedScore, weight };
    }

    // in thông báo về gpa
    public static void report(double midTermScore, double finalTermScore, double homeworkScore) {
        double totalScore = midTermScore + finalTermScore + homeworkScore;
        System.out.println("Overall percentage = " + String.format("%.1f", totalScore));
        double grade;
        if (totalScore >= 85) {
            grade = 3;
        } else if (totalScore >= 75) {
            grade = 2;
        } else if (totalScore >= 60) {
            grade = 1;
        } else {
            grade = 0;
        }
        System.out.println("Your grade will be at least: " + grade);
        System.out.println(grade >= 4 ? "You are an excellent student"
                : (grade >= 3 ? "You are great student"
                        : (grade >= 2 ? "Not bad" : "You need to study and practice more")));
    }
}
