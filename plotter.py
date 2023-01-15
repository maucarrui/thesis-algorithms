# A simple plotter for the .csv files obtained.

import sys

import pandas as pd
import matplotlib.pyplot as plt


# Auxiliary function to print the usage of the file:
def usage():
    string = "python plotter.py <data.csv> <plot-title> <x-label> <y-label> <output>"
    return string


if __name__ == "__main__":
    # Check that only one argument is passed.
    if len(sys.argv) != 6:
        print(usage())
    else:
        # Set variables for the arguments
        file_name = sys.argv[1]
        plot_title = sys.argv[2]
        x_label = sys.argv[3]
        y_label = sys.argv[4]
        output = sys.argv[5]

        # Build a dataframe with the csv values.
        df = pd.read_csv(file_name)

        # Build a plot that contains the values.
        plt.plot(df["Size"], df["Time"], marker="o")

        # Set the title and labels.
        plt.title(plot_title)
        plt.xlabel(x_label)
        plt.ylabel(y_label)
        plt.grid()

        # Fix the margins.
        plt.margins(x=0)
        plt.ylim(ymin=0)
        plt.tight_layout()

        plt.savefig(output)
