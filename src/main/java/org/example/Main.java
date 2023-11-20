package org.example;

import java.util.*;

/*
 * проверка парадокса Монти-Холла на примере трех шкатулок в одной из которыйх миллион
 *
 * 1й вариант: игрок случайно выбирает одну из шкатулок и не меняет свое решение,
 * и всегда остается при своём первом выборе
 *
 * 2й вариант: игрок случайно выбирает одну из дверей, далее ведущий убирает одну пустую из оставшихся
 * и перелагает сменить шкатулку на что пользователь соглашается
 *
 * в каждом случае подсчитаем число выигрышей во множестве итераций и переведем в процентное отношение
 *
 * */
public class Main {
    public static void main(String[] args) {
        Random rnd = new Random();
        Map<Integer, Boolean> result = new HashMap<>();
        int maxSteps = 1000; // число итераций


        // реализация первого варианта
        double counterWin = 0;
        for (int i = 0; i < maxSteps; i++) {
            boolean[] boxes = getBoxes(); //перемешиваем шкатулки
            int selectedBox = rnd.nextInt(3); // игрок выбирает одну из шкатулок
            if (boxes[selectedBox]) counterWin++; //проверяем выиграл ли он и сохраняем результат
            result.put(i, boxes[selectedBox]);
        }
        System.out.printf("Результат по первому варианту при %d итераций: %.3f%%\n", maxSteps, (counterWin / maxSteps * 100));

        // реализация второго врианта
        counterWin = 0;
        for (int i = 0; i < maxSteps; i++) {
            boolean[] boxes = getBoxes();// перемешиваем шкатулки
            int selectedBox = rnd.nextInt(3);//игрок выбирает одну из шкатулок
            selectedBox = getSecondBox(selectedBox, boxes); //игрок меняет выбранную шкатулку на ту, что предложит ведущий
            if (boxes[selectedBox]) counterWin++;
            result.put(i + maxSteps, boxes[selectedBox]);//проверяем выиграл ли он и сохраняем результат
        }
        System.out.printf("Результат по второму варианту при %d итераций: %.3f%%\n", maxSteps, (counterWin / maxSteps * 100));
    }

    /**
     * Возвращает индекс шкатулки, которую предлагает открыть ведущий
     *
     * @param userSelectedBox
     * @param boxes
     * @return
     */
    private static int getSecondBox(int userSelectedBox, boolean[] boxes) {
        Random rnd = new Random();
        int selectSecondBox;
        if (boxes[userSelectedBox]) {
            // начальный выбор игрока был правильный
            while (true) {
                //предлагаем любую из оставшихся шкатулок
                selectSecondBox = rnd.nextInt(3);
                if (selectSecondBox != userSelectedBox) return selectSecondBox;
            }
        } else {
            //если начальный выбор был проигрышный,
            // то предлагаем шкатулку с призом
            while (true) {
                for (int i = 0; i < 3; i++) {
                    if (boxes[i]) return i;
                }
            }
        }
    }

    /**
     * Возвращает массив шкатулок, в одной из которого приз
     *
     * @return
     */
    private static boolean[] getBoxes() {

        Random rnd = new Random();
        boolean[] boxes = new boolean[]{false, false, false};///создание шкатулок
        boxes[rnd.nextInt(3)] = true;/// случайный выбор выйграшной шкатулки
        return boxes;
    }
}