x = [0.01 0.02 0.025 0.1	0.15  0.2	0.25 0.3	0.35 0.4	0.46	0.5		0.54	0.6		0.65	0.7];
y =	[1    0.93 0.95  0.92	0.889 0.87	0.86	 0.84	0.75 0.71	0.64	0.55	0.48	0.38	0.29	0.16];

plot(x, y, 'or', x, y, '--');
%hold on;

%legend('Variation tendency');
legend('Measure Data', 'Fitting Curve', 'FontSize',16);

%axes('linewidth',1, 'box', 'on', 'FontSize',16);
title("Aggregated precision/recall curve of Jiagu", 'FontSize', 16);
xlabel('Recall','FontSize',16);
ylabel("Precision", 'FontSize',16);
grid on;
hold on;