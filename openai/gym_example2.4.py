import gym
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

env = gym.make('Taxi-v3')
random_policy = np.ones([env.env.nS, env.env.nA]) / env.env.nA


def random_policy_steps_count():
    state = env.reset()
    counter = 0
    reward = None
    while reward != 20:
        state, reward, done, info = env.step(env.action_space.sample())
        counter += 1
    return counter


counts = [random_policy_steps_count() for i in range(1000)]
sns.distplot(counts)
plt.title("Distribution of number of steps needed")
plt.show()

print("An agent using Random search takes about an average of "
      + str(int(np.mean(counts)))
      + " steps to successfully complete its mission.")
